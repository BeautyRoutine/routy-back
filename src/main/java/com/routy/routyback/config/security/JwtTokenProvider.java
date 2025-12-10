package com.routy.routyback.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import com.routy.routyback.domain.user.User;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;

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

    // JWT 토큰 생성 - userLevel 포함
    public String createToken(String userId, Integer userLevel) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userLevel", userLevel);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        String userId = claims.getSubject();
        Integer userLevel = claims.get("userLevel", Integer.class);

        // DB에서 사용자 상태 확인 (탈퇴 회원 체크)
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        // USERSTATUS가 0이면 탈퇴한 회원
        if (user.getUserStatus() != null && user.getUserStatus() == 0) {
            throw new RuntimeException("DEACTIVATED_USER");
        }

        // userLevel에 따라 권한 부여
        String role = (userLevel != null && userLevel == 9) ? "ROLE_ADMIN" : "ROLE_USER";

        return new UsernamePasswordAuthenticationToken(
            userId,
            "",
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}