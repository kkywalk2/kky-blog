package com.example.blog.dto;

public enum ResponseCode {
    OK("OK"),
    UNKNOWN_ERROR("Unknown error occurred!");

    private String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
