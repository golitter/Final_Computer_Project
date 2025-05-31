package com.golemon.blogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.golemon.blogbackend.enums.BlogSystemConstantsEnum;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.mapper.ArticleMapper;
import com.golemon.blogbackend.common.domain.dto.ArticleDTO;
import com.golemon.blogbackend.common.domain.dto.ArticleQueryDTO;
import com.golemon.blogbackend.common.domain.entity.Article;
import com.golemon.blogbackend.common.domain.entity.ArticleTag;
import com.golemon.blogbackend.common.domain.entity.Category;
import com.golemon.blogbackend.common.domain.entity.Tag;
import com.golemon.blogbackend.common.vo.*;
import com.golemon.blogbackend.service.ArticleService;
import com.golemon.blogbackend.service.ArticleTagService;
import com.golemon.blogbackend.service.CategoryService;
import com.golemon.blogbackend.service.TagService;
import com.golemon.blogbackend.utils.BeanCopyUtils;
import com.golemon.blogbackend.utils.LocalDateTimeUtil;
import com.golemon.blogbackend.utils.RedisCache;
import com.golemon.exception.BlogSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Article> listNormalArticle() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        return list(wrapper);
    }

    @Override
    public Long getNormalArticleCount() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        return count(wrapper);
    }

    @Override
    public ResponseResult<?> getHotArticleList() {
        // Query non-draft, non-deleted articles, sorted by popularity in descending order, top 5 articles
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        wrapper.orderByDesc(Article::getViewCount);
        // wrapper.last("limit 5");

        Page<Article> page = new Page<>(1, 5);
        this.page(page, wrapper);

        List<Article> records = page.getRecords();
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(records, HotArticleVo.class));
    }

    @Override
    public ResponseResult<?> getArticleList(ArticleQueryDTO articleQueryDTO) {
        // Construct query conditions
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // ARTICLE_STATUS_NORMAL = 0 represents normal status
        wrapper.eq(Article::getStatus, 0);
        wrapper.orderByDesc(Article::getCreateTime);
        wrapper.eq(articleQueryDTO.getCategoryId() != null, Article::getCategoryId, articleQueryDTO.getCategoryId());

        // Add title fuzzy search
        wrapper.like(articleQueryDTO.getTitle() != null, Article::getTitle, articleQueryDTO.getTitle());

        if (articleQueryDTO.getTagId() != null) {
            LambdaQueryWrapper<ArticleTag> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.eq(ArticleTag::getTagId, articleQueryDTO.getTagId());
            List<ArticleTag> articleTags = articleTagService.list(tagWrapper);
            wrapper.in(Article::getId, articleTags.stream().map(ArticleTag::getArticleId).collect(Collectors.toList()));
        }

        if (articleQueryDTO.getDate() != null) {
            try {
                Map<String, LocalDate> dateRange = LocalDateTimeUtil.getDateRange(articleQueryDTO.getDate());
                wrapper.between(Article::getCreateTime, dateRange.get("start"), dateRange.get("end"));
            } catch (ParseException e) {
                throw new BlogSystemException(HttpStatusCodeEnum.DATE_NOT_VALID);
            }
        }

        // Query from database with pagination
        Page<Article> page = new Page<>(articleQueryDTO.getPageNum(), articleQueryDTO.getPageSize());
        this.page(page, wrapper);
        List<Article> articles = page.getRecords();

        // Set article category name
        for (Article article : articles) {
            String categoryName = categoryService.getById(article.getCategoryId()).getName();
            article.setCategoryName(categoryName);
        }

        List<ArticlesVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticlesVo.class);
        return ResponseResult.okResult(new PageVo<>(page.getTotal(), articleListVos));
    }

    @Override
    public ResponseResult<?> getArticleDetail(Long id) {
        // Query article from database
        Article article = getById(id);
        ArticleDetailsVo articleDetailsVO = BeanCopyUtils.copyBean(article, ArticleDetailsVo.class);

        // Set category name
        Category category = categoryService.getById(article.getCategoryId());
        if (category != null) {
            articleDetailsVO.setCategoryName(category.getName());
        }

        // Set tags
        LambdaQueryWrapper<ArticleTag> articleTagWrapper = new LambdaQueryWrapper<>();
        articleTagWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTags = articleTagService.list(articleTagWrapper);
        List<Long> tagIds = articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());

        if (tagIds.size() > 0) {
            LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.in(Tag::getId, tagIds);
            List<Tag> tags = tagService.list(tagWrapper);
            articleDetailsVO.setTags(BeanCopyUtils.copyBeanList(tags, TagVo.class));
        }

        return ResponseResult.okResult(articleDetailsVO);
    }

    @Override
    public ResponseResult<?> getArticleCount() {
        Long article = getNormalArticleCount();
        long category = categoryService.count();
        long tag = tagService.count();
        return ResponseResult.okResult(new ArticleCountVo(article, category, tag));
    }

    @Override
    public List<Article> listViewCount() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getViewCount);
        return list(queryWrapper);
    }

    @Override
    public ResponseResult<?> updateViewCount(Long id) {
        // Don't check if id exists, so we can directly update view count for newly published articles
        redisCache.increaseCacheMapValue(String.valueOf(BlogSystemConstantsEnum.REDIS_ARTICLE_VIEW_COUNT_KEY.getValue()), id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> getPreviousNextArticle(Long id) {
        // Query current article
        Article article = getById(id);
        PreviousNextArticleVo previousNextArticleVo = new PreviousNextArticleVo();

        // Query previous article (largest ID smaller than current article)
        LambdaQueryWrapper<Article> previousWrapper = new LambdaQueryWrapper<>();
        previousWrapper.lt(Article::getId, id);  // ID smaller than current article
        previousWrapper.eq(Article::getStatus, BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        previousWrapper.orderByDesc(Article::getId).last("limit 1");  // Get largest ID
        Article previousArticle = getOne(previousWrapper);
        if (previousArticle != null) {
            previousNextArticleVo.setPrevious(BeanCopyUtils.copyBean(previousArticle, HotArticleVo.class));
        } else {
            previousNextArticleVo.setPrevious(null);
        }

        // Query next article (smallest ID larger than current article)
        LambdaQueryWrapper<Article> nextWrapper = new LambdaQueryWrapper<>();
        nextWrapper.gt(Article::getId, id);  // ID larger than current article
        nextWrapper.eq(Article::getStatus, BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        nextWrapper.orderByAsc(Article::getId).last("limit 1");  // Get smallest ID
        Article nextArticle = getOne(nextWrapper);
        if (nextArticle != null) {
            previousNextArticleVo.setNext(BeanCopyUtils.copyBean(nextArticle, HotArticleVo.class));
        } else {
            previousNextArticleVo.setNext(null);
        }

        return ResponseResult.okResult(previousNextArticleVo);
    }

    @Override
    public ResponseResult<?> addArticle(ArticleDTO article) {
        log.info("Received article: {}", article);  // Output received article object

        Article newArticle = BeanCopyUtils.copyBean(article, Article.class);
        // Set category id
        Category category = categoryService.getOrAddCategoryByName(article.getCategory());
        newArticle.setCategoryId(category.getId());

        // Set article status
        String status = String.valueOf(article.getIsDraft() ? BlogSystemConstantsEnum.ARTICLE_STATUS_DRAFT.getValue() : BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        System.out.println("test" + status);
        newArticle.setStatus(status);
        saveOrUpdate(newArticle);

        // Set tags
        List<ArticleTag> articleTags = article.getTags().stream()
                .map(name -> new ArticleTag(newArticle.getId(), tagService.getOrAddTagByName(name).getId()))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult(newArticle.getId());
    }

    @Override
    public ResponseResult<?> editArticle(ArticleDTO article) {
        // Remove old tags of the article
        LambdaQueryWrapper<ArticleTag> articleTagWrapper = new LambdaQueryWrapper<>();
        articleTagWrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(articleTagWrapper);

        return addArticle(article);
    }

    @Override
    public ResponseResult<?> deleteArticle(Long id) {
        // Check if article exists
        Article article = getById(id);

        // Delete article related tags
        LambdaQueryWrapper<ArticleTag> articleTagWrapper = new LambdaQueryWrapper<>();
        articleTagWrapper.eq(ArticleTag::getArticleId, id);
        articleTagService.remove(articleTagWrapper);

        // Delete article
        removeById(id);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> getRandomArticleList() {
        // Query non-draft, non-deleted articles
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        
        // Get all article IDs that meet the conditions
        List<Article> allArticles = list(wrapper);
        if (allArticles.isEmpty()) {
            return ResponseResult.okResult(Collections.emptyList());
        }

        // Randomly select 3 articles
        List<Article> randomArticles = new ArrayList<>();
        Random random = new Random();
        int totalArticles = allArticles.size();
        int targetSize = Math.min(3, totalArticles);  // Modified to 3 articles

        // Use Set to ensure no duplicate articles are selected
        Set<Integer> selectedIndexes = new HashSet<>();
        while (selectedIndexes.size() < targetSize) {
            selectedIndexes.add(random.nextInt(totalArticles));
        }

        // Get articles based on randomly selected indexes
        for (Integer index : selectedIndexes) {
            randomArticles.add(allArticles.get(index));
        }

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(randomArticles, HotArticleVo.class));
    }
}