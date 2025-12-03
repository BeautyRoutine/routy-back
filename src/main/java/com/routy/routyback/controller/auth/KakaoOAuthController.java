package com.routy.routyback.controller.auth;

import com.routy.routyback.service.user.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class KakaoOAuthController {

    private final KakaoAuthService kakaoAuthService;

    /**
     * 카카오 로그인 시작
     * - 브라우저를 카카오 인증 페이지로 바로 리다이렉트
     *   GET http://localhost:8080/auth/kakao/login
     */
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String kakaoUrl = kakaoAuthService.getKakaoLoginUrl();
        response.sendRedirect(kakaoUrl);
    }

    /**
     * 카카오 콜백
     * - GET /auth/kakao/callback?code=xxxx
     * - 현재는 KakaoAuthService.kakaoLogin 에서 더미 응답
     */
    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code) {
        return kakaoAuthService.kakaoLogin(code);
    }
}
