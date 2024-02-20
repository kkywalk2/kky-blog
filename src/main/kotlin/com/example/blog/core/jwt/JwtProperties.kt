package com.example.blog.core.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
    val issuer: String = "kky",
    val secret: String,
)
