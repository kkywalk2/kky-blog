package com.example.blog.security

import com.example.blog.core.TokenProvider
import com.example.blog.user.domains.UserDomain
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

class JwtAuthorizationFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val token = header.replace("Bearer ", "")

        try {
            val name = tokenProvider.getClaimByName(token, "name")
            val email = tokenProvider.getClaimByName(token, "email")
            val id = tokenProvider.getClaimByName(token, "id")

            if (name == null || email == null || id == null) throw java.lang.RuntimeException("")

            val userDomain = UserDomain(
                id = id.toLong(),
                name = name,
                encodedPassword = "",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                email = email
            )

            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                userDomain,
                null,
                userDomain.authorities
            )
        } catch (ex: RuntimeException) {
            logger.warn("JWT token validation failed: ${ex.message}", ex)
            SecurityContextHolder.clearContext()
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token")
            return
        }

        chain.doFilter(request, response)
    }
}
