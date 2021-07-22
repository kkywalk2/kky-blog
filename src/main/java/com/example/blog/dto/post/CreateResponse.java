package com.example.blog.dto.post;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import lombok.Getter;

@Getter
public class CreateResponse extends Response {
	public CreateResponse(ResponseCode code, String message) {
		super(code, message);
	}
}
