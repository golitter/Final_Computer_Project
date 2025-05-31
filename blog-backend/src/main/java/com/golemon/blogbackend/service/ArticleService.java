package com.golemon.blogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.golemon.blogbackend.common.domain.dto.ArticleDTO;
import com.golemon.blogbackend.common.domain.dto.ArticleQueryDTO;
import com.golemon.blogbackend.common.domain.entity.Article;
import com.golemon.blogbackend.common.vo.ResponseResult;

import java.util.List;
public interface ArticleService extends IService<Article> {
    List<Article> listNormalArticle();
    Long getNormalArticleCount();

    ResponseResult<?> getHotArticleList();

    ResponseResult<?> getArticleList(ArticleQueryDTO articleQueryDTO);

    ResponseResult<?> getArticleDetail(Long id);

    ResponseResult<?> getArticleCount();

    List<Article> listViewCount();

    ResponseResult<?> updateViewCount(Long id);

    ResponseResult<?> getPreviousNextArticle(Long id);

    ResponseResult<?> addArticle(ArticleDTO article);

    ResponseResult<?> editArticle(ArticleDTO article);

    ResponseResult<?> deleteArticle(Long id);

    ResponseResult<?> getRandomArticleList();
}
