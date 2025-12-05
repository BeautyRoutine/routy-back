package com.routy.routyback.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 인증 불필요 엔드포인트 → 필터 통과
        if (uri.startsWith("/api/auth") ||
            uri.startsWith("/api/products") ||
            uri.startsWith("/api/categories") ||
            uri.startsWith("/api/search") ||
            uri.startsWith("/api/ingredient") ||
            uri.startsWith("/api/reviews") ||
            uri.startsWith("/api/dibs") ||
            uri.startsWith("/api/users") ||
            uri.equals("/") ||
            uri.equals("/index.html")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 필요한 요청 처리
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}