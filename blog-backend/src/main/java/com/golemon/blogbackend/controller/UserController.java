package com.golemon.blogbackend.controller;

import com.golemon.blogbackend.common.domain.dto.LoginUserDTO;
import com.golemon.blogbackend.common.domain.entity.User;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult<?> getUserInfo(){
        return userService.getUserInfo();
    }


    @GetMapping("/adminInfo")
    public ResponseResult<?> getAdminInfo(){
        return userService.getAdminInfo();
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@Valid @RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseResult<?> login(@Valid @RequestBody LoginUserDTO user) {
        return userService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult<?> logout(){
        return userService.logout();
    }
}
