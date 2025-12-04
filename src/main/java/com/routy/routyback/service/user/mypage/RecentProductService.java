package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.RecentProductResponse;
import com.routy.routyback.mapper.user.RecentProductMapper;
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

    /**
     * 최근 본 상품 추가 또는 갱신
     * - 이미 본 상품이면 시간만 업데이트
     * - 처음 본 상품이면 신규 등록
     * - 사용자 기준 최대 10개 유지
     */
    @Override
    public void addRecentProduct(Long userNo, Long prdNo, Long prdSubCate) {
        int exists = recentProductMapper.exists(userNo, prdNo);

        if (exists > 0) {
            // 이미 본 상품 → 시간 갱신
            recentProductMapper.updateViewTime(userNo, prdNo);
        } else {
            // 처음 본 상품 → 신규 등록
            recentProductMapper.insertRecentProduct(userNo, prdNo, prdSubCate);
        }

        // 최대 10개 유지
        recentProductMapper.trimRecentProducts(userNo);
    }

    /**
     * 최근 본 상품 목록 조회
     * @param userNo 사용자 번호(Long)
     */
    @Override
    public List<RecentProductResponse> getRecentProducts(Long userNo) {
        return recentProductMapper.getRecentViewedProducts(userNo);
    }

    /**
     * userId(loginId)로 userNo(PK) 조회
     *
     * @param userId 로그인 ID
     * @return 사용자 번호(PK)
     */
    @Override
    public Long getUserNoByUserId(String userId) {
        return recentProductMapper.getUserNoByUserId(userId);
    }
}