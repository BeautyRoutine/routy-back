package com.routy.routyback.service.user;

import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.domain.auth.RefreshToken;
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

    public AuthResponse login(LoginRequest req) {

        User user = userMapper.findByUserId(req.getUserId());
        if (user == null) throw new IllegalArgumentException("존재하지 않는 계정입니다.");

        if (!passwordEncoder.matches(req.getUserPw(), user.getUserPw())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String access = jwt.createAccessToken(user.getUserId(), user.getUserLevel());
        String refresh = jwt.createRefreshToken(user.getUserId());

        refreshTokenMapper.saveRefreshToken(new RefreshToken(user.getUserId(), refresh));

        return new AuthResponse(access, user);
    }

    public String refreshAccessToken(String refreshToken) {

        if (!jwt.validate(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String userId = jwt.getUserId(refreshToken);

        RefreshToken saved = refreshTokenMapper.findByUserId(userId);
        if (saved == null || !saved.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Token mismatch");
        }

        User user = userMapper.findByUserId(userId);
        return jwt.createAccessToken(user.getUserId(), user.getUserLevel());
    }
}
