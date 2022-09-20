package com.example.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AccountDetailService jwtUserDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, AccountDetailService jwtUserDetailsService, JwtUtil jwtUtil) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUserDetailsService, jwtUtil))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/post").authenticated()
                .antMatchers(HttpMethod.PUT, "/post").authenticated()
                .antMatchers(HttpMethod.DELETE, "/post/**").authenticated()
                .antMatchers(HttpMethod.GET, "/account").authenticated()
                .antMatchers("/comment").authenticated()
                .anyRequest().permitAll()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }
}