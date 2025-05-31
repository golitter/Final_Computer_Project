package com.golemon.exception;

import com.golemon.blogbackend.enums.HttpStatusCodeEnum;

/**
 * Used to handle business-related exceptions in the blog system. This class utilizes the HttpStatusCodeEnum
 * to define error codes and messages, enabling centralized management of exception information and status codes.
 */
public class BlogSystemException extends RuntimeException {
    private int status;
    private String message;

    public BlogSystemException(HttpStatusCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.status = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMsg();
    }

    public int getCode() {
        return status;
    }

    public void setCode(int code) {
        this.status = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}
