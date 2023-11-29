package com.example.blog.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtUserDetailsService: AccountDetailService,
    private val jwtUtil: JwtUtil
)  {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http {
            cors {  }
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthorizationFilter(jwtUserDetailsService, jwtUtil))
            authorizeRequests {
                authorize(HttpMethod.POST, "/api/post", authenticated)
                authorize(HttpMethod.PUT, "/api/post", authenticated)
                authorize(HttpMethod.DELETE, "/api/post/**", authenticated)
                authorize(HttpMethod.GET, "/api/account", authenticated)
                authorize("/api/comment", authenticated)
                authorize(anyRequest, permitAll)
            }
            exceptionHandling { authenticationEntryPoint = jwtAuthenticationEntryPoint }
        }
        return http.orBuild
    }
}