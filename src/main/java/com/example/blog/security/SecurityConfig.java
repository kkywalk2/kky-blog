package com.example.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private AccountDetailService jwtUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, AccountDetailService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        http.csrf().disable()
                /*.authorizeRequests().antMatchers(
                "/",
                "/favicon.ico",
                "/js/**",
                "/css/**",
                "/account/signup",
                "/account/signin",
                "/image/**")
                .permitAll()
                .anyRequest().authenticated()*/
                .authorizeRequests().antMatchers(
                    HttpMethod.POST, "/post"
                ).authenticated()
                .antMatchers("/comment").authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .headers().frameOptions().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}