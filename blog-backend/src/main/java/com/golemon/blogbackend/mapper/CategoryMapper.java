package com.golemon.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.golemon.blogbackend.common.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

