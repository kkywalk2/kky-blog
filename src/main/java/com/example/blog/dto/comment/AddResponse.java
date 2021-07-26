package com.example.blog.dto.comment;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import lombok.Getter;

@Getter
public class AddResponse extends Response {
    public AddResponse(ResponseCode code, String message) {
        super(code, message);
    }
}
