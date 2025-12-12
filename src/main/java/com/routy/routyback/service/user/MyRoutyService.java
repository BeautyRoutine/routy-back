package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.myrouty.MyProductResponse;
import com.routy.routyback.mapper.user.MyRoutyMapper;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyRoutyService {                              // My Routy 비즈니스 로직 담당 서비스

    private final MyRoutyMapper myRoutyMapper;     // MyRouty 관련 조회용 매퍼
    private final UserMapper userMapper;           // userId → userNo 변환용 매퍼

    /**
     * 내 제품 목록 조회 (userId 기반)
     *
     * 1) userId(String) → userNo(Long) 변환
     * 2) userNo 기반으로 구매 제품 목록 조회
     */
    public List<MyProductResponse> getMyProducts(String userId) {
        Long userNo = userMapper.findUserNoByUserId(userId);  // 문자열 userId → PK userNo 로 변환
        return myRoutyMapper.selectMyProducts(userNo);        // userNo 기준으로 구매한 제품 목록 조회
    }
}