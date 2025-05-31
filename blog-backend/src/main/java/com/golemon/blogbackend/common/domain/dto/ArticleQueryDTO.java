package com.golemon.blogbackend.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQueryDTO {
    private Integer pageNum = 1;

    @Min(value = 1, message = "Number of items per page cannot be less than 1")
    @Max(value = 40, message = "Number of items per page cannot be greater than 40")
    private Integer pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String title;

    @Pattern(regexp = "^((19)|(2\\d))\\d{2}/((0?[1-9])|1[012])$", message = "Date format error")
    private String date;
}