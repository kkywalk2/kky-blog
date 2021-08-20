package com.example.blog.dto.post;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import lombok.Getter;

@Getter
public class DeleteResponse extends Response {
	public DeleteResponse(ResponseCode code, String message) {
		super(code, message);
	}
}
