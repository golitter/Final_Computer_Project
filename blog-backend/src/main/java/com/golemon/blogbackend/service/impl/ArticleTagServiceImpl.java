package com.golemon.blogbackend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.golemon.blogbackend.mapper.ArticleTagMapper;
import com.golemon.blogbackend.common.domain.entity.ArticleTag;
import com.golemon.blogbackend.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

