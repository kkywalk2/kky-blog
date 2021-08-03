package com.example.blog.dto.post;

import java.util.List;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;

import lombok.Getter;

@Getter
public class GetPostsResponse extends Response {
    private List<GetPostsData> data;
    public GetPostsResponse(ResponseCode code, String message, List<GetPostsData> data) {
		super(code, message);
        this.data = data;
	}
}
