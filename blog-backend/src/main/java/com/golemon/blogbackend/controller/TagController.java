package com.golemon.blogbackend.controller;

import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tagCountList")
    public ResponseResult<?> getTagCountList() {
        return tagService.getTagCountList();
    }
} 