package com.example.blog.dto

import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.Optional

data class UserDto (
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
    val name: String,
    @field:Size(min = 8, max = 30)
    val password: String
)
