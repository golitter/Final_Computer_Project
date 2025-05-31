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
@TableName("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -54158634882013443L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String userName;
    private String signature;
    private String password;
    private String type;
    private String status;
    private String email;
    private String phoneNumber;
    private String sex;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer delFlag;
}
