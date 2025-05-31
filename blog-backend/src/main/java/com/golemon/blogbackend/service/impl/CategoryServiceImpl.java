package com.golemon.blogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.golemon.blogbackend.enums.BlogSystemConstantsEnum;
import com.golemon.blogbackend.mapper.CategoryMapper;
import com.golemon.blogbackend.common.domain.entity.Article;
import com.golemon.blogbackend.common.domain.entity.Category;
import com.golemon.blogbackend.common.vo.CategoryCountVo;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.service.ArticleService;
import com.golemon.blogbackend.service.CategoryService;
import com.golemon.blogbackend.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult<?> getCategoryCountList() {
        // Query category IDs of non-draft articles from database
        List<Article> articles = articleService.listNormalArticle();
        Set<Long> categoryIds = articles.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        // Query categories from database
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Category::getId, categoryIds);
        queryWrapper.eq(Category::getStatus, BlogSystemConstantsEnum.CATEGORY_STATUS_NORMAL.getValue());
        List<Category> categories = list(queryWrapper);
        List<CategoryCountVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryCountVo.class);

        // Count the number of articles for each category
        Map<Long, Integer> categoryIdCountMap = new HashMap<>();
        for (Article article : articles) {
            Long categoryId = article.getCategoryId();
            Integer count = categoryIdCountMap.get(categoryId);
            categoryIdCountMap.put(categoryId, count == null ? 1 : count + 1);
        }

        for (CategoryCountVo categoryVo : categoryVos) {
            categoryVo.setCount(categoryIdCountMap.get(categoryVo.getId()));
        }

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public Category getOrAddCategoryByName(String name) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getName, name);
        Category category = getOne(wrapper);

        if (category == null) {
            category = new Category();
            category.setName(name);
            save(category);
        }

        return category;
    }
}