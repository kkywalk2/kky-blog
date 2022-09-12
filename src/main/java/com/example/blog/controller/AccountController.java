package com.example.blog.controller;

import com.example.blog.dto.account.*;
import com.example.blog.service.AccountService;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto signUp(@Valid @RequestBody SignUpRequest req) {
        AccountDto accountDto = accountService.createAccount(req.getAccountName(), req.getPassword(), req.getEmail());
        return accountDto;
    }

    @PostMapping("/signin")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AuthDto signIn(@Valid @RequestBody SignInRequest req) {
        AuthDto authDto = accountService.accountValidation(req.getAccountName(), req.getPassword());
        return authDto;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String AuthCheck() {
        return "OK";
    }
}
