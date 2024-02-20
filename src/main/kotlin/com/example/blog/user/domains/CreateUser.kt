package com.example.blog.user.domains

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class CreateUser(
    @field:Size(min = 8, max = 30)
    val name: String,
    @field:Size(min = 8, max = 30)
    val password: String,
    @field:Email
    val email: String,
)
