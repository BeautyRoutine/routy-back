package com.routy.routyback.service.user;

import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.domain.user.RefreshToken;
import com.routy.routyback.domain.user.User;
import com.routy.routyback.dto.auth.AuthResponse;
import com.routy.routyback.dto.auth.LoginRequest;
import com.routy.routyback.dto.auth.SignupRequest;
import com.routy.routyback.mapper.user.RefreshTokenMapper;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwt;

    // ================================
    // 회원가입
    // ================================
    public String signup(SignupRequest request) {
        User exists = userMapper.findByUserId(request.getUserId());
        if (exists != null) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserPwd(passwordEncoder.encode(request.getUserPwd()));
        user.setUserName(request.getUserName());
        user.setUserLevel(1);

        userMapper.insertUser(user);
        return "success";
    }

    // ================================
    // 로그인
    // ================================
    public AuthResponse login(LoginRequest request) {
        User user = userMapper.findByUserId(request.getUserId());
        if (user == null) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        if (!passwordEncoder.matches(request.getUserPwd(), user.getUserPwd())) {
            throw new RuntimeException("INVALID_PASSWORD");
        }

        // Access Token
        String accessToken = jwt.createToken(user.getUserId(), user.getUserLevel());

        // Refresh Token 생성 + DB 저장
        RefreshToken refresh = new RefreshToken(user.getUserId(), jwt.createToken(user.getUserId(), 0));
        refreshTokenMapper.save(refresh);

        return new AuthResponse(accessToken, user, refresh.getRefreshToken());
    }

    // ================================
    // 토큰 재발급
    // ================================
    public AuthResponse refresh(String refreshToken) {

        RefreshToken saved = refreshTokenMapper.find(refreshToken);
        if (saved == null) {
            throw new RuntimeException("INVALID_REFRESH_TOKEN");
        }

        // 저장된 refreshToken 유효성 검사
        if (!jwt.validateToken(saved.getRefreshToken())) {
            throw new RuntimeException("EXPIRED_REFRESH_TOKEN");
        }

        String userId = saved.getUserId();
        User user = userMapper.findByUserId(userId);

        // 새로운 Access Token 발급
        String newAccessToken = jwt.createToken(userId, user.getUserLevel());

        return new AuthResponse(newAccessToken, user, saved.getRefreshToken());
    }
}
