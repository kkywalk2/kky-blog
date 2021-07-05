package com.example.blog.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AccountSignInReq {
	@Size(min = 8, max = 30)
    private String accountName;
	@Size(min = 8, max = 30)
    private String password;
}
