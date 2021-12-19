package com.example.blog.security;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NonNull;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.ExpiredJwtException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AccountDetailService accountDetailService;
    private final JwtUtil jwtUtil;

    JwtRequestFilter(AccountDetailService accountDetailService, JwtUtil jwtUtil) {
        this.accountDetailService = accountDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException ex) {
                log.error("Unable to get JWT token", ex);
                throw new IllegalArgumentException("Unable to get JWT token");
            } catch (ExpiredJwtException ex) {
                log.error("JWT Token has expired", ex);
                throw new ExpiredJwtException(null, null, "JWT Token has expired");
            } catch (Exception ex) {
                log.error("token valid error:" + ex.getMessage(), ex);
                throw new RuntimeException("11 Username from token error");
            }
        } else {
            log.debug("JWT token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            AccountDetail accountDetail = this.accountDetailService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, accountDetail)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                accountDetail,
                                null,
                                accountDetail.getAuthorities()
                        );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
