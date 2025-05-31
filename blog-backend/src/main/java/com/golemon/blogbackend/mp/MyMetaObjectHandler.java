package com.golemon.blogbackend.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.golemon.blogbackend.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // @TODO: Creator and updater need to be removed
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // Use LocalDateTime to replace Date
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}