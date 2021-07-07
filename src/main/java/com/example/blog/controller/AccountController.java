package com.example.blog.controller;

import com.example.blog.dto.*;
import com.example.blog.service.AccountService;
import com.example.blog.security.JwtUtil;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

	AccountController(AccountService accountService, JwtUtil jwtUtil){
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }
	
    @PostMapping("/signup")
    @ResponseBody
    public AccountSignUpRes signUp(@Valid @RequestBody AccountSignUpReq req){
    	accountService.createAccount(req.getAccountName(),req.getPassword(), req.getEmail());
        return new AccountSignUpRes("OK","");
    }
    
    @PostMapping("/signin")
    @ResponseBody
    public AccountSignInRes signIn(@Valid @RequestBody AccountSignInReq req){
        return new AccountSignInRes("OK","", jwtUtil.createToken(req.getAccountName()));
    }
}
