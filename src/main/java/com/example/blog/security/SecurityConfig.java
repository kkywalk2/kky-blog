package com.example.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AccountDetailService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
        // ��ġ�ϴ� �ڰ������� ���� ����ڸ� �ε��� ��ġ�� �˼� �ֵ���
        // AuthenticationManager�� �����Ѵ�.
        // BCryptPasswordEncoder�� �̿�
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors().disable()
                .csrf().disable()
                // �� ��û�� ������ ���� �ʴ´�.
                .authorizeRequests().antMatchers(
                        "/account/signup",
                        "/account/signin")
                .permitAll()
                // �ٸ� ��� ��û�� ������ �Ѵ�.
                .anyRequest().authenticated().and()
                // ���¾��� ������ �̿��ϸ�, ���ǿ� ������� ���¸� �������� �ʴ´�.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .headers().frameOptions().disable();
        // ��� ��û�� ��ū�� �����ϴ� ���͸� �߰��Ѵ�.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}