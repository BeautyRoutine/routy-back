package com.routy.routyback.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 카카오 로그인 처리 서비스
 * - 빌드 오류 제거용 기본 구조
 * - 실제 토큰 요청 및 사용자 정보 조회는 추후 구현
 */
@Service
public class KakaoAuthService {

    // 카카오 인가 코드 요청용 기본값 (필요 시 properties로 이동)
    private static final String KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String CLIENT_ID = "f78b556c577e2d5abd6a921f789ac2c9"; // REST API 키
    private static final String REDIRECT_URI = "http://localhost:8080/auth/kakao/callback";

    /**
     * 카카오 로그인 URL 생성
     * Controller에서 호출됨
     */
    public String getKakaoLoginUrl() {
        String encodedRedirect = URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);

        return KAKAO_AUTH_URL +
                "?response_type=code" +
                "&client_id=" + CLIENT_ID +
                "&redirect_uri=" + encodedRedirect;
    }

    /**
     * 카카오 redirect에서 받은 code 처리
     * 현재는 더미 응답
     */
    public ResponseEntity<?> kakaoLogin(String code) {
        return ResponseEntity.ok("Kakao login success (dummy). code=" + code);
    }
}
