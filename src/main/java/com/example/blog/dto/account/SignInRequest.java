package com.example.blog.dto.account;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignInRequest {
	@Size(min = 8, max = 30)
    private String accountName;
	@Size(min = 8, max = 30)
    private String password;
}
