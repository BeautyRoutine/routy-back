package com.routy.routyback.service.user;

import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.domain.user.User;
import com.routy.routyback.dto.user.LoginRequest;
import com.routy.routyback.dto.user.SignupRequest;
import com.routy.routyback.dto.user.UserResponse;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 로컬 로그인/회원가입 비즈니스 로직
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     */
    public UserResponse signup(SignupRequest request) {
        // 아이디 중복 체크
        User existing = userMapper.findByUserId(request.getUserId());
        if (existing != null) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }

        User user = User.builder()
                .userId(request.getUserId())
                .userPw(passwordEncoder.encode(request.getPassword()))
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .userPhone(request.getUserPhone())
                .userAuth("ROLE_USER")
                .userStatus(1)
                .build();

        userMapper.insertUser(user);

        return UserResponse.from(userMapper.findByUserId(request.getUserId()));
    }

    /**
     * 로그인 → JWT 토큰 발급
     */
    public String login(LoginRequest request) {
        User user = userMapper.findByUserId(request.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getUserPw())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return jwtTokenProvider.createToken(user.getUserId());
    }
}
