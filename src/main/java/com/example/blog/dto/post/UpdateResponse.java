package com.example.blog.dto.post;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;

import lombok.Getter;

@Getter
public class UpdateResponse extends Response {
	public UpdateResponse(ResponseCode code, String message) {
		super(code, message);
	}
}
