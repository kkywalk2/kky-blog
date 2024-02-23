package com.example.blog.user.controllers

import com.example.blog.dto.UserDto
import com.example.blog.dto.AuthDto
import com.example.blog.dto.SignInRequest
import com.example.blog.user.domains.CreateUser
import com.example.blog.user.services.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@Valid @RequestBody req: CreateUser): UserDto {
        return userService.createAccount(req)
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@Valid @RequestBody request: SignInRequest): AuthDto {
        return userService.createToken(request, LocalDateTime.now())
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun authCheck(): String {
        return "OK"
    }
}
