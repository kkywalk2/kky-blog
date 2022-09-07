package com.example.blog.dto.post;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class GetPostsResponse extends Response {
    private Page<PostDto> data;
    public GetPostsResponse(ResponseCode code, String message, Page<PostDto> data) {
		super(code, message);
        this.data = data;
	}
}
