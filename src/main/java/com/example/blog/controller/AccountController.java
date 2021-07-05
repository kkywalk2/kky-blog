package com.example.blog.controller;

import com.example.blog.dto.AccountSignInReq;
import com.example.blog.dto.AccountSignInRes;
import com.example.blog.dto.AccountSignUpReq;
import com.example.blog.dto.AccountSignUpRes;
import com.example.blog.repository.AccountRepository;
import com.example.blog.security.JwtUtil;
import com.example.blog.entity.AccountEntity;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;

    @Autowired
	AccountController(AccountRepository accountRepository, JwtUtil jwtUtil){
        this.accountRepository = accountRepository;
        this.jwtUtil = jwtUtil;
    }
	
    @PostMapping("/signup")
    @ResponseBody
    public AccountSignUpRes signUp(@Valid @RequestBody AccountSignUpReq req){
    	accountRepository.save(new AccountEntity(req.getAccountName(),req.getPassword(), req.getEmail()));
        return new AccountSignUpRes("OK","");
    }
    
    @PostMapping("/signin")
    @ResponseBody
    public AccountSignInRes signIn(@Valid @RequestBody AccountSignInReq req){
        return new AccountSignInRes("OK","", jwtUtil.createToken(req.getAccountName()));
    }
}
