package com.routy.routyback.service.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.domain.user.User;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret:}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.authorize-uri}")
    private String authorizeUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getKakaoLoginUrl() {
        return UriComponentsBuilder.fromHttpUrl(authorizeUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .toUriString();
    }

    public void kakaoLogin(String code, HttpServletResponse response) throws IOException {
        try {
            String accessToken = getAccessToken(code);
            KakaoUserInfo userInfo = getKakaoUserInfo(accessToken);
            User user = processKakaoUser(userInfo);
            String jwtToken = jwtTokenProvider.createToken(user.getUserId(), user.getUserLevel());
            boolean needsSkinProfile = user.getUserSkin() == null || user.getUserSkin() == 0;
            System.out.println("===== 카카오 로그인 디버그 =====");
            System.out.println("userId: " + user.getUserId());
            System.out.println("userName: " + user.getUserName());
            System.out.println("userSkin: " + user.getUserSkin());
            System.out.println("needsSkinProfile: " + needsSkinProfile);

            String frontendUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:3000/kakao/callback")
                    .queryParam("token", jwtToken)
                    .queryParam("userId", user.getUserId())
                    .queryParam("userName", URLEncoder.encode(user.getUserName(), StandardCharsets.UTF_8))
                    .queryParam("userLevel", user.getUserLevel())
                    .queryParam("userSkin", user.getUserSkin() != null ? user.getUserSkin() : 0)
                    .queryParam("isNewUser", false)
                    .queryParam("needsSkinProfile", needsSkinProfile)
                    .toUriString();
            System.out.println("리다이렉트 URL: " + frontendUrl);

            response.sendRedirect(frontendUrl);

        } catch (Exception e) {
            System.err.println("ì¹´ì¹´ì˜¤ ë¡œê·¸ì¸ ì‹¤íŒ¨: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("http://localhost:3000/login?error=" + 
                URLEncoder.encode("ì¹´ì¹´ì˜¤ ë¡œê·¸ì¸ì— ì‹¤íŒ¨í–ˆìŠµë‹ˆë‹¤", StandardCharsets.UTF_8));
        }
    }

    private String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        if (clientSecret != null && !clientSecret.isEmpty()) {
            params.add("client_secret", clientSecret);
        }
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("ì•¡ì„¸ìŠ¤ í† í° ë°œê¸‰ ì‹¤íŒ¨: " + e.getMessage());
        }
    }

    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                request,
                String.class
        );

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            
            String kakaoId = rootNode.path("id").asText();
            String nickname = rootNode.path("properties").path("nickname").asText();
            
            JsonNode emailNode = rootNode.path("kakao_account").path("email");
            String email = emailNode.isMissingNode() || emailNode.isNull() 
                ? "kakao_" + kakaoId + "@temp.com"
                : emailNode.asText();

            return new KakaoUserInfo(kakaoId, email, nickname);
        } catch (Exception e) {
            throw new RuntimeException("ì‚¬ìš©ìž ì •ë³´ ì¡°íšŒ ì‹¤íŒ¨: " + e.getMessage());
        }
    }

    private User processKakaoUser(KakaoUserInfo userInfo) {
        String kakaoUserId = "kakao_" + userInfo.getKakaoId();
        
        User existingUser = userMapper.findByUserId(kakaoUserId);

        if (existingUser != null) {
            return existingUser;
        }

        User newUser = new User();
        newUser.setUserId(kakaoUserId);
        newUser.setUserPw("KAKAO_NO_PASSWORD");
        newUser.setUserName(userInfo.getNickname());
        newUser.setUserNick(userInfo.getNickname());
        newUser.setUserEmail(userInfo.getEmail());
        newUser.setUserHp("01000000000");
        
        newUser.setUserZip(0);
        newUser.setUserJibunAddr("ë¯¸ìž…ë ¥");
        newUser.setUserRoadAddr("ë¯¸ìž…ë ¥");
        newUser.setUserDetailAddr("ë¯¸ìž…ë ¥");
        
        newUser.setUserSkin(0);
        newUser.setPhoneVerified("N");  // ì¹´ì¹´ì˜¤ ë¡œê·¸ì¸ì€ SMS ì¸ì¦ ë¶ˆí•„ìš”

        userMapper.insertUser(newUser);

        return userMapper.findByUserId(kakaoUserId);
    }

    private static class KakaoUserInfo {
        private final String kakaoId;
        private final String email;
        private final String nickname;

        public KakaoUserInfo(String kakaoId, String email, String nickname) {
            this.kakaoId = kakaoId;
            this.email = email;
            this.nickname = nickname;
        }

        public String getKakaoId() { return kakaoId; }
        public String getEmail() { return email; }
        public String getNickname() { return nickname; }
    }
    @PostConstruct
public void checkValues() {
    System.out.println(">>> CLIENT_ID = " + clientId);
    System.out.println(">>> CLIENT_SECRET = " + clientSecret);
    System.out.println(">>> REDIRECT_URI = " + redirectUri);
}

}