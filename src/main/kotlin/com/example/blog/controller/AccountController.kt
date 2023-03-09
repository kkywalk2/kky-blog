package com.example.blog.controller

import com.example.blog.dto.AccountDto
import com.example.blog.dto.AuthDto
import com.example.blog.dto.SignInRequest
import com.example.blog.dto.SignUpRequest
import com.example.blog.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody request: @Valid SignUpRequest): AccountDto {
        return accountService.createAccount(request)
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody request: @Valid SignInRequest): AuthDto {
        return accountService.accountValidation(request)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun authCheck(): String {
        return "OK"
    }
}