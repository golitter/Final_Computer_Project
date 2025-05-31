package com.golemon.blogbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.golemon.blogbackend.common.domain.dto.ArticleDTO;
import com.golemon.blogbackend.common.domain.dto.ArticleQueryDTO;
import com.golemon.blogbackend.common.domain.entity.Article;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.mapper.ArticleMapper;
import com.golemon.blogbackend.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static com.golemon.blogbackend.common.vo.ResponseResult.okResult;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("/hotArticleList")
    public ResponseResult<?> getHotArticleList() {
        return articleService.getHotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult<?> getArticleList(@Valid ArticleQueryDTO articleQueryDTO){
        return articleService.getArticleList(articleQueryDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('article:add')")
    public ResponseResult<?> addArticle(@Valid @RequestBody ArticleDTO article){
        System.out.println("abc" + article);
        return articleService.addArticle(article);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('article:edit')")
    public ResponseResult<?> editArticle(@Valid @RequestBody ArticleDTO article){
        return articleService.editArticle(article);
    }

    @GetMapping("/count")
    public ResponseResult<?> getCountInfo() {
        Map<String, Integer> countInfo = articleMapper.getCountInfo();
        return ResponseResult.okResult(countInfo);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult<?> updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/previousNextArticle/{id}")
    public ResponseResult<?> getPreviousNextArticle(@PathVariable Long id){
        return articleService.getPreviousNextArticle(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('article:delete')")
    public ResponseResult<?> deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }

    @GetMapping("/randomArticleList")
    public ResponseResult<?> getRandomArticleList() {
        return articleService.getRandomArticleList();
    }

    @GetMapping("/search")
    public ResponseResult<?> searchArticles(@RequestParam String keyword) {
        List<Article> articles = articleMapper.searchByTitle(keyword);
        return ResponseResult.okResult(articles);
    }

}
