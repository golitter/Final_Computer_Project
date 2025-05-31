package com.golemon.blogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.mapper.AccessMapper;
import com.golemon.blogbackend.mapper.UserMapper;
import com.golemon.blogbackend.common.LoginUser;
import com.golemon.blogbackend.common.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccessMapper accessMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        User user = userMapper.selectOne(wrapper);
        Assert.notNull(user, String.valueOf(HttpStatusCodeEnum.LOGIN_ERROR));

        List<String> permissions = accessMapper.selectPermissionsByUserId(user.getId());
        return new LoginUser(user, permissions);
    }
}
