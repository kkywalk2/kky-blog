package com.example.blog.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.Optional

data class AccountDto (
    var name: String,
    var email: String,
    val createdAt: Optional<LocalDateTime>,
    val updatedAt: Optional<LocalDateTime>,
)

data class AuthDto (
    var name: String,
    var token: String,
)

data class SignInRequest (
    @field:Size(min = 8, max = 30)
    val accountName: String,
    @field:Size(min = 8, max = 30)
    val password: String
)

data class SignUpRequest (
    @field:Size(min = 8, max = 30)
    val accountName: String,
    @field:Size(min = 8, max = 30)
    val password: String,
    @field:Email
    val email: String
)
