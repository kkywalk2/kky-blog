package com.example.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AccountDetailService accountDetailService;

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountDetailService accountDetailService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.accountDetailService = accountDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            String accountName = jwtUtil.getAccountNameFromToken(token);

            Optional.ofNullable(accountName).ifPresent(
                    s -> {
                        AccountDetail accountDetail = accountDetailService.loadUserByUsername(s);
                        Authentication authentication =
                                new UsernamePasswordAuthenticationToken(
                                        accountDetail,
                                        null,
                                        accountDetail.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            );
        } catch (RuntimeException ex) {
            //TODO Something
        }

        chain.doFilter(request, response);
    }
}
