package com.golemon.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.golemon.blogbackend.common.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    
    @Select("SELECT " +
            "(SELECT COUNT(*) FROM article) as articleCount, " +
            "(SELECT COUNT(*) FROM category) as categoryCount, " +
            "(SELECT COUNT(*) FROM tag) as tagCount")
    Map<String, Integer> getCountInfo();

    @Select("SELECT * FROM article WHERE title LIKE CONCAT('%', #{keyword}, '%')")
    List<Article> searchByTitle(String keyword);
}
