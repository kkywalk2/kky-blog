package com.example.blog.dto.account;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;

public class SignUpResponse extends Response {
	public SignUpResponse(ResponseCode code, String message) {
		super(code, message);
	}
}
