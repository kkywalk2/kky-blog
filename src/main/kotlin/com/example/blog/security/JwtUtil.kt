package com.example.blog.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUtil(
    @Value("\${jwt.secret}") private val secret: String
) {

    fun createToken(accountName: String?): String {
        return JWT.create()
            .withSubject(accountName)
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .withClaim("accountName", accountName)
            .sign(Algorithm.HMAC512(secret))
    }

    fun getAccountNameFromToken(token: String?): String {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("accountName").asString()
    }

    companion object {
        private const val JWT_TOKEN_VALIDITY = 5 * 60 * 60
    }
}