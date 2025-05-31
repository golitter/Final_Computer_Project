package com.golemon.blogbackend.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCountVo {
    private Long article;
    private Long category;
    private Long tag;
}
