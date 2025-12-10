package com.routy.routyback.controller.auth;

import com.routy.routyback.service.user.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth/kakao")
@RequiredArgsConstructor
public class KakaoOAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = kakaoAuthService.getKakaoLoginUrl();
        return new RedirectView(kakaoLoginUrl);
    }

    @GetMapping("/login")
    public RedirectView kakaoLoginAlias() {
        String kakaoLoginUrl = kakaoAuthService.getKakaoLoginUrl();
        return new RedirectView(kakaoLoginUrl);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam String code, HttpServletResponse response) throws IOException {
        kakaoAuthService.kakaoLogin(code, response);
    }
}