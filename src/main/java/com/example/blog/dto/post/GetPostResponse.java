package com.example.blog.dto.post;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import com.example.blog.entity.PostEntity;

import lombok.Getter;

@Getter
public class GetPostResponse extends Response {
    private PostEntity data;
    public GetPostResponse(ResponseCode code, String message, PostEntity data) {
        super(code, message);
        this.data = data;
    }
}
