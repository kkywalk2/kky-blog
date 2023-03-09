package com.example.blog.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtUserDetailsService: AccountDetailService,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .addFilter(JwtAuthorizationFilter(jwtUserDetailsService, jwtUtil, authenticationManager()))
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/post").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/post").authenticated()
            .antMatchers(HttpMethod.DELETE, "/api/post/**").authenticated()
            .antMatchers(HttpMethod.GET, "/api/account").authenticated()
            .antMatchers("/api/comment").authenticated()
            .anyRequest().permitAll()
            .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
    }
}