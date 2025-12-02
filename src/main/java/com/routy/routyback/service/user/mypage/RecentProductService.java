package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.RecentProductResponse;
import com.routy.routyback.mapper.user.mypage.RecentProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 최근 본 상품 서비스 구현
 */
@Service
@RequiredArgsConstructor
public class RecentProductService implements IRecentProductService {

    private final RecentProductMapper mapper;

    @Override
    public List<RecentProductResponse> getRecentProducts(Long userNo) {
        return mapper.getRecentViewedProducts(userNo);
    }
}