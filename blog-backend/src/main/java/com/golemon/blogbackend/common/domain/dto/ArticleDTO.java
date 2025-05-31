package com.golemon.blogbackend.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;

    @NotBlank(message = "Blog title cannot be empty")
    private String title;

    /**
     * Category name
     */
    @NotBlank(message = "Blog category cannot be empty")
    private String category;

    @NotBlank(message = "Blog content cannot be empty")
    private String content;

    @Size(max = 1024, message = "Summary length cannot exceed 1024 characters")
    private String summary;

    /**
     * Tag name list
     */
    private List<String> tags;

    @NotNull(message = "Must specify whether the blog is a draft")
    private Boolean isDraft;
}
