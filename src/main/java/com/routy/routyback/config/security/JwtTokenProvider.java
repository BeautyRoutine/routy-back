package com.routy.routyback.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.routy.routyback.domain.user.User;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    private Key key;

    private final UserMapper userMapper;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // ================================
    // 1) 기존 코드와 호환되는 "토큰 생성"
    // ================================
    public String createToken(String userId, Integer userLevel) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userLevel", userLevel);

        Date now = new Date();
        Date expire = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ================================
    // 2) 기존 코드와 호환되는 "토큰 인증 정보"
    // ================================
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        String userId = claims.getSubject();
        Integer userLevel = claims.get("userLevel", Integer.class);

        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        if (user.getUserStatus() != null && user.getUserStatus() == 0) {
            throw new RuntimeException("DEACTIVATED_USER");
        }

        String role = (userLevel != null && userLevel == 9) ? "ROLE_ADMIN" : "ROLE_USER";

        return new UsernamePasswordAuthenticationToken(
                userId,
                "",
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    // ================================
    // 3) 토큰에서 userId 추출
    // ================================
    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    // ================================
    // 4) Request Header에서 토큰 꺼내기
    // ================================
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    // ================================
    // 5) 기존 validateToken() 그대로 복원
    // ================================
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ================================
    // 내부 공용 메서드
    // ================================
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
