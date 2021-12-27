package com.example.blog.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public String createToken(String accountName) {
        return JWT.create()
                .withSubject(accountName)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .withClaim("accountName", accountName)
                .sign(Algorithm.HMAC512(secret));
    }

    public String getAccountNameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("accountName").asString();
    }
}