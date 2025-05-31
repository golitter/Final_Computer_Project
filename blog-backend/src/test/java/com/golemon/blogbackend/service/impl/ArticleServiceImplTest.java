package com.golemon.blogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.golemon.blogbackend.common.domain.dto.ArticleDTO;
import com.golemon.blogbackend.common.domain.dto.ArticleQueryDTO;
import com.golemon.blogbackend.common.domain.entity.Article;
import com.golemon.blogbackend.common.domain.entity.ArticleTag;
import com.golemon.blogbackend.common.domain.entity.Category;
import com.golemon.blogbackend.common.domain.entity.Tag;
import com.golemon.blogbackend.enums.BlogSystemConstantsEnum;
import com.golemon.blogbackend.mapper.ArticleMapper;
import com.golemon.blogbackend.service.ArticleTagService;
import com.golemon.blogbackend.service.CategoryService;
import com.golemon.blogbackend.service.TagService;
import com.golemon.blogbackend.utils.RedisCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ArticleTagService articleTagService;

    @Mock
    private TagService tagService;

    @Mock
    private RedisCache redisCache;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article testArticle;
    private Category testCategory;
    private Tag testTag;
    private ArticleTag testArticleTag;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testArticle = new Article();
        testArticle.setId(1L);
        testArticle.setTitle("测试文章");
        testArticle.setContent("测试内容");
        testArticle.setStatus(BlogSystemConstantsEnum.ARTICLE_STATUS_NORMAL.getValue());
        testArticle.setCategoryId(1L);
        testArticle.setViewCount(100L);

        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("测试分类");

        testTag = new Tag();
        testTag.setId(1L);
        testTag.setName("测试标签");

        testArticleTag = new ArticleTag();
        testArticleTag.setArticleId(1L);
        testArticleTag.setTagId(1L);
    }

    @Test
    void testListNormalArticle() {
        // 准备数据
        List<Article> expectedArticles = Collections.singletonList(testArticle);
        when(articleMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(expectedArticles);

        // 执行测试
        List<Article> result = articleService.listNormalArticle();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testArticle.getId(), result.get(0).getId());
        verify(articleMapper).selectList(any(LambdaQueryWrapper.class));
    }

    @Test
    void testGetNormalArticleCount() {
        // 准备数据
        when(articleMapper.selectCount(any(LambdaQueryWrapper.class))).thenReturn(1L);

        // 执行测试
        Long count = articleService.getNormalArticleCount();

        // 验证结果
        assertEquals(1L, count);
        verify(articleMapper).selectCount(any(LambdaQueryWrapper.class));
    }

    @Test
    void testGetArticleDetail() {
        // 准备数据
        when(articleMapper.selectById(1L)).thenReturn(testArticle);
        when(categoryService.getById(1L)).thenReturn(testCategory);
        when(articleTagService.list(any(LambdaQueryWrapper.class)))
                .thenReturn(Collections.singletonList(testArticleTag));
        when(tagService.list(any(LambdaQueryWrapper.class)))
                .thenReturn(Collections.singletonList(testTag));

        // 执行测试
        var result = articleService.getArticleDetail(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        verify(articleMapper).selectById(1L);
        verify(categoryService).getById(1L);
        verify(articleTagService).list(any(LambdaQueryWrapper.class));
        verify(tagService).list(any(LambdaQueryWrapper.class));
    }

    @Test
    void testUpdateViewCount() {
        // 准备数据
        when(redisCache.increaseCacheMapValue(
                anyString(),
                anyString(),
                anyLong()
        )).thenReturn(1L);

        // 执行测试
        var result = articleService.updateViewCount(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        verify(redisCache).increaseCacheMapValue(
                eq(String.valueOf(BlogSystemConstantsEnum.REDIS_ARTICLE_VIEW_COUNT_KEY.getValue())),
                eq("1"),
                eq(1L)
        );
    }

    @Test
    void testGetHotArticleList() {
        // 准备数据
        Page<Article> page = new Page<>(1, 5);
        page.setRecords(Collections.singletonList(testArticle));
        when(articleMapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class))).thenReturn(page);

        // 执行测试
        var result = articleService.getHotArticleList();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        verify(articleMapper).selectPage(any(Page.class), any(LambdaQueryWrapper.class));
    }

    @Test
    void testGetArticleCount() {
        // 准备数据
        when(articleMapper.selectCount(any(LambdaQueryWrapper.class))).thenReturn(10L);
        when(categoryService.count()).thenReturn(5L);
        when(tagService.count()).thenReturn(20L);

        // 执行测试
        var result = articleService.getArticleCount();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        verify(articleMapper).selectCount(any(LambdaQueryWrapper.class));
        verify(categoryService).count();
        verify(tagService).count();
    }

    @Test
    void testGetRandomArticleList() {
        // 准备数据
        List<Article> allArticles = Arrays.asList(testArticle, new Article(), new Article());
        when(articleMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(allArticles);

        // 执行测试
        var result = articleService.getRandomArticleList();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        verify(articleMapper).selectList(any(LambdaQueryWrapper.class));
    }


} 