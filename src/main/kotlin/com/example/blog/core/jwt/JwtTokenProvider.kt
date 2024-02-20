package com.example.blog.core.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.blog.core.TokenProvider
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
) : TokenProvider {

    override fun createToken(claims: Map<String, String>, now: LocalDateTime): String {
        return JWT.create()
            .withSubject(claims["email"])
            .withHeader(mapOf("typ" to "JWT"))
            .withIssuer(jwtProperties.issuer)
            .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .withPayload(claims)
            .sign(Algorithm.HMAC512(jwtProperties.secret))
    }

    override fun validateToken(token: String, now: LocalDateTime): Boolean {
        return runCatching { JWT.require(Algorithm.HMAC512(jwtProperties.secret)).build().verify(token) }.isSuccess
    }

    override fun getClaimByName(token: String, claimName: String): String? {
        return runCatching {
            JWT.require(Algorithm.HMAC512(jwtProperties.secret)).build().verify(token).getClaim(claimName).asString()
        }.getOrNull()
    }

    companion object {
        private const val JWT_TOKEN_VALIDITY = 5 * 60 * 60
    }

}
