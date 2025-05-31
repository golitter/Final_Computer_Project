package com.golemon.blogbackend.common.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("access")
public class Access implements Serializable {

    @Serial
    private static final long serialVersionUID = -54158634882013443L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String accessName;

    private String permission;

    private String status;

}


