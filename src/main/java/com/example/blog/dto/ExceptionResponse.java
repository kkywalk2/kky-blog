package com.example.blog.dto;

import lombok.Getter;

@Getter
public class ExceptionResponse extends Response{
	public ExceptionResponse(ResponseCode code, String message) {
		super(code, message);
	}
}
