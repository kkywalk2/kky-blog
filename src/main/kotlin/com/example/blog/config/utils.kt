package com.example.blog.config

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.*

fun <T : Any> Iterable<T>.firstOptional(): Optional<T> = Optional.ofNullable(firstOrNull())

object Base64ObjectMapper {
    private val objectMapper: ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    fun toBase64(): String {
        val json = objectMapper.writeValueAsString(this)
        return Base64.getUrlEncoder().encodeToString(json.toByteArray())
    }

    fun <T> fromBase64(base64String: String): T {
        val decoded = String(Base64.getUrlDecoder().decode(base64String))

        return objectMapper.readValue(decoded, object : TypeReference<T>() {})
    }
}
