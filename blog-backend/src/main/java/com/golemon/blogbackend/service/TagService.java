package com.golemon.blogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.golemon.blogbackend.common.domain.entity.Tag;
import com.golemon.blogbackend.common.vo.ResponseResult;

public interface TagService extends IService<Tag> {
    Tag getOrAddTagByName(String name);
    ResponseResult<?> getTagCountList();
}
