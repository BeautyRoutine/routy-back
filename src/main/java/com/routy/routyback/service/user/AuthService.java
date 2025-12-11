package com.routy.routyback.service.user;

import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.domain.user.User;
import com.routy.routyback.dto.auth.AuthResponse;
import com.routy.routyback.dto.auth.LoginRequest;
import com.routy.routyback.dto.auth.SignupRequest;
import com.routy.routyback.mapper.user.UserMapper;
import com.routy.routyback.mapper.user.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse signup(SignupRequest request) {

        if (userMapper.existsByEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (userMapper.findByUserId(request.getUserId()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserPw(passwordEncoder.encode(request.getUserPw()));
        user.setUserName(request.getUserName());

        long timestamp = System.currentTimeMillis() % 100000;
        user.setUserNick("사용자" + timestamp);

        user.setUserHp(request.getUserHp());
        user.setUserEmail(request.getUserEmail());

        user.setUserZip(request.getUserZip());
        user.setUserJibunAddr(request.getUserJibunAddr());
        user.setUserRoadAddr(request.getUserRoadAddr());
        user.setUserDetailAddr(request.getUserDetailAddr());

        if (request.getUserBirth() != null && !request.getUserBirth().isEmpty()) {
            user.setUserBirth(request.getUserBirth());
        }

        if (request.isPhoneVerified()) {
            user.setPhoneVerified("Y");
            user.setPhoneVerifiedAt(LocalDateTime.now());
        } else {
            user.setPhoneVerified("N");
        }

        userMapper.insertUser(user);

        User savedUser = userMapper.findByUserId(request.getUserId());
        if (savedUser == null)
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.");

        // 액세스 + 리프레시 토큰 생성
        String accessToken = jwtTokenProvider.createToken(savedUser.getUserId(), savedUser.getUserLevel());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        refreshTokenMapper.saveRefreshToken(savedUser.getUserId(), refreshToken);

        return new AuthResponse(accessToken, savedUser, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userMapper.findByUserId(request.getUserId());
        if (user == null) throw new IllegalArgumentException("존재하지 않는 계정입니다.");
        if (user.getUserStatus() != null && user.getUserStatus() == 0)
            throw new IllegalArgumentException("탈퇴한 계정입니다.");

        if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        String accessToken = jwtTokenProvider.createToken(user.getUserId(), user.getUserLevel());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        refreshTokenMapper.saveRefreshToken(user.getUserId(), refreshToken);

        return new AuthResponse(accessToken, user, refreshToken);
    }

    // ==============================
    // 리프레시 토큰으로 액세스 토큰 재발급
    // ==============================
    public String refreshAccessToken(String userId, String refreshToken) {

        String savedToken = refreshTokenMapper.getRefreshToken(userId);

        if (savedToken == null || !savedToken.equals(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
        }

        return jwtTokenProvider.createToken(userId, 1);
    }
}
