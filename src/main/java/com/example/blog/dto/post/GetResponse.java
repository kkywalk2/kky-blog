package com.example.blog.dto.post;

import java.util.List;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import com.example.blog.entity.PostEntity;

import lombok.Getter;

@Getter
public class GetResponse extends Response {
    private List<PostEntity> data;
    public GetResponse(ResponseCode code, String message, List<PostEntity> data) {
		super(code, message);
        this.data = data;
	}
}
