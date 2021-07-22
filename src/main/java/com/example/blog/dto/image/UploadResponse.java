package com.example.blog.dto.image;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import lombok.Getter;

@Getter
public class UploadResponse extends Response {
    private String fileName;
    public UploadResponse(ResponseCode code, String message, String fileName) {
        super(code, message);
        this.fileName = fileName;
    }
}
