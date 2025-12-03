package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.RecentProductResponse;
import com.routy.routyback.mapper.user.RecentProductMapper;
import com.routy.routyback.mapper.user.UserMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 최근 본 상품 서비스 구현
 */
@Service
@RequiredArgsConstructor
public class RecentProductService implements IRecentProductService {

    private final RecentProductMapper recentProductMapper;
    private final UserMapper userMapper;

    /**
     * 최근 본 상품 목록 조회
     * @param userId 사용자 아이디(문자열)
     */
    @Override
    public List<RecentProductResponse> getRecentProducts(String userId) {
        Long userNo = userMapper.findUserNoByUserId(userId);
        if (userNo == null) {
            return List.of();
        }
        return recentProductMapper.getRecentViewedProducts(userNo);
    }
}