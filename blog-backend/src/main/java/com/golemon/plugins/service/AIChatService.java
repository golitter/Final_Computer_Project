package com.golemon.plugins.service;


import com.golemon.blogbackend.common.vo.ResponseResult;
import reactor.core.publisher.Mono;

public interface AIChatService {
    ResponseResult<?> chat(String message);
} 