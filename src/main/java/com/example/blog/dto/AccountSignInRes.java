package com.example.blog.dto;

import lombok.Getter;

@Getter
public class AccountSignInRes extends Response{
	private String token;
	public AccountSignInRes(String status, String message, String token) {
		super(status, message);
		this.token = token;
	}
}
