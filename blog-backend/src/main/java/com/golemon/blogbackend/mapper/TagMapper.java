package com.golemon.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.golemon.blogbackend.common.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

