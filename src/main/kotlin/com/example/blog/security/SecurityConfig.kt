package com.example.blog.security

import com.example.blog.core.TokenProvider
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
    private val tokenProvider: TokenProvider,
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
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthorizationFilter(tokenProvider))
            authorizeRequests {
                authorize(HttpMethod.POST, "/api/posts", authenticated)
                authorize(HttpMethod.PUT, "/api/posts/**", authenticated)
                authorize(HttpMethod.DELETE, "/api/posts/**", authenticated)
                authorize(HttpMethod.POST, "/api/image", authenticated)
                authorize(HttpMethod.GET, "/api/account", authenticated)
                authorize("/api/comment", authenticated)
                authorize(anyRequest, permitAll)
            }
            exceptionHandling { authenticationEntryPoint = jwtAuthenticationEntryPoint }
        }
        return http.orBuild
    }
}