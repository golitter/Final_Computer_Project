package com.golemon.plugins.controller;

import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.plugins.service.AIChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/plugins/ai")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    @PostMapping(value = "/chat")
    public ResponseResult<?> chat(@RequestBody String message) {
        return aiChatService.chat(message);
    }
} 