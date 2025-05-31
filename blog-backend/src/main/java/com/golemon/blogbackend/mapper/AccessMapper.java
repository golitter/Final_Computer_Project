package com.golemon.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.golemon.blogbackend.common.domain.entity.Access;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccessMapper extends BaseMapper<Access> {
    List<String> selectPermissionsByUserId(Long id);
}
