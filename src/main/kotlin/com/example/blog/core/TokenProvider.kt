package com.example.blog.core

import java.time.LocalDateTime

interface TokenProvider {

    fun createToken(claims: Map<String, String>, now: LocalDateTime): String

    fun validateToken(token: String, now: LocalDateTime): Boolean

    fun getClaimByName(token: String, claimName: String): String?

}
