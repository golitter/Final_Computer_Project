package com.golemon.blogbackend.handle.security;

import com.alibaba.fastjson.JSON;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        ResponseResult<?> result = ResponseResult.errorResult(HttpStatusCodeEnum.NOT_OPERATOR_AUTH, e.getMessage());
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
