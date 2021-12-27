package com.example.blog.controller;

import com.example.blog.dto.*;
import com.example.blog.dto.account.SignInRequest;
import com.example.blog.dto.account.SignInResponse;
import com.example.blog.dto.account.SignUpRequest;
import com.example.blog.dto.account.SignUpResponse;
import com.example.blog.service.AccountService;
import com.example.blog.security.JwtUtil;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.google.common.base.Preconditions;

@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    AccountController(AccountService accountService, JwtUtil jwtUtil) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    @ResponseBody
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest req) {
        accountService.createAccount(req.getAccountName(), req.getPassword(), req.getEmail());
        return new SignUpResponse(ResponseCode.OK, "");
    }

    @PostMapping("/signin")
    @ResponseBody
    public SignInResponse signIn(@Valid @RequestBody SignInRequest req) {
        Preconditions.checkState(accountService.accountValidation(req.getAccountName(), req.getPassword()), "Account info mismatch");
        return new SignInResponse(ResponseCode.OK, "", jwtUtil.createToken(req.getAccountName()));
    }

    @GetMapping
    @ResponseBody
    public Response AuthCheck() {
        return new Response(ResponseCode.OK, "");
    }
}
