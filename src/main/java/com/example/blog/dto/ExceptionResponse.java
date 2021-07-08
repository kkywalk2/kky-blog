package com.example.blog.dto;

import lombok.Getter;

@Getter
public class ExceptionResponse extends Response{
	public ExceptionResponse(String status, String message) {
		super(status, message);
	}
}
