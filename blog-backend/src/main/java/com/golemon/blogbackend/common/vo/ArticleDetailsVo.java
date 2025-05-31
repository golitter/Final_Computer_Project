package com.golemon.blogbackend.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailsVo {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String categoryName;
    private Long categoryId;
    List<TagVo> tags;

    private Long viewCount;

    private LocalDateTime createTime;
}
