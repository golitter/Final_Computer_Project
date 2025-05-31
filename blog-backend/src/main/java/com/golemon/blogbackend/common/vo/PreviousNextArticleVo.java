package com.golemon.blogbackend.common.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviousNextArticleVo {
    private HotArticleVo previous;
    private HotArticleVo next;
}
