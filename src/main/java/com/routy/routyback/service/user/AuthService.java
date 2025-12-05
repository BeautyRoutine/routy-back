package com.routy.routyback.service.user;

import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.domain.user.User;
import com.routy.routyback.dto.auth.AuthResponse;
import com.routy.routyback.dto.auth.LoginRequest;
import com.routy.routyback.dto.auth.SignupRequest;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        // 이메일 중복 체크
        if (userMapper.existsByEmail(request.getUserEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 아이디 중복 체크
        if (userMapper.findByUserId(request.getUserId()) != null) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        // User 객체 생성
        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserPw(passwordEncoder.encode(request.getUserPw()));
        user.setUserName(request.getUserName());
        long timestamp = System.currentTimeMillis() % 100000;
        user.setUserNick("사용자" + timestamp);
        user.setUserHp(request.getUserHp());
        user.setUserEmail(request.getUserEmail());
        
        // 주소 정보 (필수)
        user.setUserZip(request.getUserZip());
        user.setUserJibunAddr(request.getUserJibunAddr());
        user.setUserRoadAddr(request.getUserRoadAddr());
        user.setUserDetailAddr(request.getUserDetailAddr());
        
        // 생년월일 (선택)
        if (request.getUserBirth() != null && !request.getUserBirth().isEmpty()) {
            user.setUserBirth(request.getUserBirth());
        }

        // 회원가입 (userNo는 DB 시퀀스로 자동 생성)
        userMapper.insertUser(user);

        // 저장된 사용자 정보 재조회 (userNo 포함)
        User savedUser = userMapper.findByUserId(request.getUserId());

        // JWT 토큰 생성 (userLevel 포함!)
        String token = jwtTokenProvider.createToken(savedUser.getUserId(), savedUser.getUserLevel());

        return new AuthResponse(token, savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        // 아이디로 사용자 조회
        User user = userMapper.findByUserId(request.getUserId());
        
        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성 (userLevel 포함!)
        String token = jwtTokenProvider.createToken(user.getUserId(), user.getUserLevel());

        return new AuthResponse(token, user);
    }
}