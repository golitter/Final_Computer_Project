package com.golemon.blogbackend.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 853190758604791410L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long pid;


    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    private Integer delFlag;


}
