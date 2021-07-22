package com.example.blog.dto.account;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import lombok.Getter;

@Getter
public class SignInResponse extends Response {
	private String token;
	public SignInResponse(ResponseCode code, String message, String token) {
		super(code, message);
		this.token = token;
	}
}
