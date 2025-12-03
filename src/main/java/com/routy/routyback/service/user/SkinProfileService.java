package com.routy.routyback.service.user;

import com.routy.routyback.config.security.JwtTokenProvider;
import com.routy.routyback.dto.user.SkinProfileRequest;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SkinProfileService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Transactional
    public void saveSkinProfile(String token, SkinProfileRequest request) {
        String bearerToken = token.replace("Bearer ", "");
        String userId = jwtTokenProvider.getUserId(bearerToken);
        Long userNo = userMapper.findUserNoByUserId(userId);

        if (userNo == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        userMapper.updateSkinType(userNo, request.getSkinType());
    }

    @Transactional(readOnly = true)
    public Integer getSkinProfile(String token) {
        String bearerToken = token.replace("Bearer ", "");
        String userId = jwtTokenProvider.getUserId(bearerToken);
        Long userNo = userMapper.findUserNoByUserId(userId);

        if (userNo == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        return userMapper.selectSkinType(userNo);
    }

    @Transactional(readOnly = true)
    public boolean isSkinProfileCompleted(String token) {
        String bearerToken = token.replace("Bearer ", "");
        String userId = jwtTokenProvider.getUserId(bearerToken);
        Long userNo = userMapper.findUserNoByUserId(userId);

        if (userNo == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        Integer skinType = userMapper.selectSkinType(userNo);
        return skinType != null && skinType != 0;
    }
}