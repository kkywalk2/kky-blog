package com.example.blog.dto;

import lombok.Getter;

@Getter
public class PostCreateRes extends Response{
	public PostCreateRes(String status, String message) {
		super(status, message);
	}
}
