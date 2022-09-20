package com.example.blog.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
    private val accountDetailService: AccountDetailService,
    private val jwtUtil: JwtUtil,
    authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val token = header.replace("Bearer ", "")

        try {
            val accountName = jwtUtil.getAccountNameFromToken(token)
            Optional.ofNullable(accountName).ifPresent {
                val accountDetail = accountDetailService.loadUserByUsername(it)
                val authentication: Authentication = UsernamePasswordAuthenticationToken(
                    accountDetail,
                    null,
                    accountDetail.authorities
                )
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: RuntimeException) {
            //TODO Something
        }

        chain.doFilter(request, response)
    }
}