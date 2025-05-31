package com.golemon.blogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.golemon.blogbackend.common.domain.entity.ArticleTag;
import com.golemon.blogbackend.common.domain.entity.Tag;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.common.vo.TagCountVo;
import com.golemon.blogbackend.mapper.TagMapper;
import com.golemon.blogbackend.service.ArticleTagService;
import com.golemon.blogbackend.service.TagService;
import com.golemon.blogbackend.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    @Transactional
    public Tag getOrAddTagByName(String name) {
        // Check if a tag with the same name already exists
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getName, name);
        Tag tag = getOne(wrapper);

        // If it doesn't exist, create a new tag
        if (tag == null) {
            tag = new Tag();
            tag.setName(name);
            save(tag);
        }

        return tag;
    }

    @Override
    public ResponseResult<?> getTagCountList() {
        try {
            // 1. Get all tags
            List<Tag> tags = list();
            if (tags.isEmpty()) {
                return ResponseResult.okResult(new ArrayList<>());
            }

            // 2. Get all article-tag associations
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            List<ArticleTag> articleTags = articleTagService.list(wrapper);

            // 3. Count the number of articles for each tag
            Map<Long, Long> tagCountMap = articleTags.stream()
                .collect(Collectors.groupingBy(ArticleTag::getTagId, Collectors.counting()));

            // 4. Build return data
            List<TagCountVo> tagCountVos = tags.stream().map(tag -> {
                TagCountVo vo = BeanCopyUtils.copyBean(tag, TagCountVo.class);
                vo.setCount(tagCountMap.getOrDefault(tag.getId(), 0L));
                return vo;
            }).collect(Collectors.toList());

            return ResponseResult.okResult(tagCountVos);
        } catch (Exception e) {
            log.error("Failed to get tag count", e);
            return ResponseResult.errorResult(500, "Failed to get tag count");
        }
    }
}
