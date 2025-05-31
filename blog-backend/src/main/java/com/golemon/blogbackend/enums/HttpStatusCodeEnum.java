package com.golemon.blogbackend.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HttpStatusCodeEnum is an enumeration class that defines commonly used HTTP status codes and their corresponding messages in the system. Each enumeration constant contains a code and a msg for unified management and usage.
 */
public enum HttpStatusCodeEnum {

    SUCCESS(200, "Operation successful"),
    NEED_LOGIN(401, "Login required"),
    NOT_OPERATOR_AUTH(403, "No operation permission"),
    RESOURCE_NOT_EXIST(404, "Requested resource does not exist"),

    SYSTEM_ERROR(500, "System error occurred"),
    USERNAME_EXIST(501, "Username already exists"),
    PHONE_NUMBER_EXIST(502, "Phone number already exists"),
    EMAIL_EXIST(503, "Email already exists"),
    LOGIN_ERROR(504, "Incorrect username or password"),
    REQUIRE_USER_INFO(505, "User information cannot be empty"),
    PARAM_NOT_VALID(506, "Invalid request parameters"),
    DATE_NOT_VALID(507, "Invalid date format");

    // @TODO: Change to status and message
    private final int status;
    private final String message;

    HttpStatusCodeEnum(int code, String errorMessage) {
        this.status = code;
        this.message = errorMessage;
    }

    public int getCode() {
        return status;
    }

    public String getMsg() {
        return message;
    }
}
