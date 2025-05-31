package com.golemon.blogbackend.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagCountVo {
    private Long id;
    private String name;
    private Long count;
}
