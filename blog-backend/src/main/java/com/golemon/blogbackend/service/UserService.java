package com.golemon.blogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.golemon.blogbackend.common.domain.dto.LoginUserDTO;
import com.golemon.blogbackend.common.domain.entity.User;
import com.golemon.blogbackend.common.vo.ResponseResult;

public interface UserService extends IService<User> {
    ResponseResult<?> getUserInfo();
    ResponseResult<?> getAdminInfo();
    ResponseResult<?> register(User user);

    ResponseResult<?> login(LoginUserDTO user);
    ResponseResult<?> logout();
}
