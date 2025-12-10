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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        // SMS 인증 체크 (개발 중에는 주석 처리 가능)
        // if (!request.isPhoneVerified()) {
        //     throw new IllegalArgumentException("휴대폰 인증이 필요합니다.");
        // }
        
        // 이메일 중복 체크
        if (userMapper.existsByEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 아이디 중복 체크
        if (userMapper.findByUserId(request.getUserId()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // User 객체 생성
        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserPw(passwordEncoder.encode(request.getUserPw()));
        user.setUserName(request.getUserName());
        
        // 닉네임 생성 (타임스탬프 기반)
        long timestamp = System.currentTimeMillis() % 100000;
        user.setUserNick("사용자" + timestamp);
        
        user.setUserHp(request.getUserHp());
        user.setUserEmail(request.getUserEmail());
        
        // 주소 정보
        user.setUserZip(request.getUserZip());
        user.setUserJibunAddr(request.getUserJibunAddr());
        user.setUserRoadAddr(request.getUserRoadAddr());
        user.setUserDetailAddr(request.getUserDetailAddr());
        
        // 생년월일 (선택)
        if (request.getUserBirth() != null && !request.getUserBirth().isEmpty()) {
            user.setUserBirth(request.getUserBirth());
        }
        
        // SMS 인증 정보 저장
        if (request.isPhoneVerified()) {
            user.setPhoneVerified("Y");
            user.setPhoneVerifiedAt(LocalDateTime.now());
        } else {
            user.setPhoneVerified("N");
        }

        // 회원가입 (userNo는 DB 시퀀스로 자동 생성, USERSTATUS=1, USERSKIN=0)
        userMapper.insertUser(user);

        // 저장된 사용자 정보 재조회
        User savedUser = userMapper.findByUserId(request.getUserId());

        if (savedUser == null) {
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(savedUser.getUserId(), savedUser.getUserLevel());

        return new AuthResponse(token, savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        // 아이디로 사용자 조회
        User user = userMapper.findByUserId(request.getUserId());
        
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 계정입니다.");
        }

        // 탈퇴한 회원 체크
        if (user.getUserStatus() != null && user.getUserStatus() == 0) {
            throw new IllegalArgumentException("탈퇴한 계정입니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getUserId(), user.getUserLevel());

        return new AuthResponse(token, user);
    }
}