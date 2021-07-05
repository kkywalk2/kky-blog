package com.example.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AccountSignUpReq {
	@Size(min = 8, max = 30)
    private String accountName;
	@Size(min = 8, max = 30)
    private String password;
    @Email
    private String email;
}

