package com.golemon.blogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.golemon.blogbackend.common.domain.entity.Category;
import com.golemon.blogbackend.common.vo.ResponseResult;


public interface CategoryService extends IService<Category> {
    ResponseResult<?> getCategoryCountList();
    Category getOrAddCategoryByName(String name);
}

