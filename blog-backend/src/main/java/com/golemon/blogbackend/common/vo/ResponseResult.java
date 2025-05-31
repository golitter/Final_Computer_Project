package com.golemon.blogbackend.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {
    private Integer status;
    private String message;
    private T data;

    public ResponseResult() {
        this.status = HttpStatusCodeEnum.SUCCESS.getCode();
        this.message = HttpStatusCodeEnum.SUCCESS.getMsg();
    }

    public static ResponseResult<?> errorResult(int code, String message) {
        return new ResponseResult<>(code, message, null);
    }

    public static ResponseResult<?> okResult() {
        return new ResponseResult<>(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getMsg(), null);
    }

    public static ResponseResult<?> okResult(int code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

    public static ResponseResult<Object> okResult(Object data) {
        return new ResponseResult<>(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getMsg(), data);
    }

    public static ResponseResult<?> errorResult(HttpStatusCodeEnum enums) {
        return new ResponseResult<>(enums.getCode(), enums.getMsg(), null);
    }

    public static ResponseResult<?> errorResult(HttpStatusCodeEnum enums, String msg) {
        return new ResponseResult<>(enums.getCode(), msg, null);
    }
}
