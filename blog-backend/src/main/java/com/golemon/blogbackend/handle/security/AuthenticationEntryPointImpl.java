package com.golemon.blogbackend.handle.security;

import com.alibaba.fastjson.JSON;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.utils.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        ResponseResult<?> result = null;

        if (e instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(HttpStatusCodeEnum.NEED_LOGIN);
        } else if (e instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(HttpStatusCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else {
            result = ResponseResult.errorResult(HttpStatusCodeEnum.SYSTEM_ERROR, "Authentication failed");
        }

        // Pass directly using jakarta.servlet.http.HttpServletResponse
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
