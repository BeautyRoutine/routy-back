package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.SkinProfileRequest;
import com.routy.routyback.mapper.user.UserMapper;
import com.routy.routyback.config.security.JwtTokenProvider;  
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkinProfileService {
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void saveSkinProfile(String token, SkinProfileRequest request) {
        try {
            String userId = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
            System.out.println("=== DEBUG ===");
            System.out.println("UserId: " + userId);
            System.out.println("SkinType: " + request.getSkinType());
            
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("skinType", request.getSkinType());
            
            int result = userMapper.updateSkinProfile(params);
            System.out.println("Update Result: " + result);
            System.out.println("=== DEBUG END ===");
            
            if (result == 0) {
                throw new RuntimeException("사용자를 찾을 수 없습니다");
            }
        } catch (Exception e) {
            System.out.println("Error in saveSkinProfile: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("피부프로필 저장 실패: " + e.getMessage());
        }
    }

    public Map<String, Object> getSkinProfile(String token) {
        String userId = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
        return userMapper.getSkinProfile(userId);
    }

    public boolean isProfileCompleted(String token) {
        String userId = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
        Map<String, Object> profile = userMapper.getSkinProfile(userId);
        return profile != null && profile.get("skinType") != null;
    }
}