package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.RankingProductResponse;
import com.routy.routyback.mapper.user.ProductRankingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품 랭킹 조회 서비스입니다.
 * - 이 서비스는 "실시간 계산"이 아니라,
 *   배치(Scheduler)를 통해 매일 자정(00:00)에 미리 계산해둔
 *   PRODUCT_RANKING_CACHE 테이블만 조회합니다.
 *
 * - 따라서 FE는 빠르게 고정된 랭킹 데이터를 받을 수 있고,
 *   대규모 트래픽에도 성능이 안정적입니다.
 */
@Service
@RequiredArgsConstructor
public class ProductRankingService implements IProductRankingService {

    // 캐시 테이블을 조회하는 Mapper (실시간 SUM 계산 없음)
    private final ProductRankingMapper productRankingMapper;

    /**
     * 카테고리별 랭킹 조회 메서드
     *
     * @param category 카테고리 코드
     *                 - null 또는 "0" → 전체 랭킹
     * @param limit FE가 요청한 최대 반환 개수
     *              - 0 이하일 경우 기본값 20 적용
     *
     * @return RankingProductResponse 리스트 (캐시 기반)
     */
    @Override
    public List<RankingProductResponse> getCategoryRanking(String category, int limit) {

        // limit 기본값 처리
        if (limit <= 0) {
            limit = 20; // FE 기본값
        }

        // category가 비어있으면 null로 통일하여 전체 캐시 조회
        if (category == null || category.equals("0") || category.isBlank()) {
            category = null;
        }

        // 캐시 테이블에서 랭킹 조회
        return productRankingMapper.getCategoryRanking(category, limit);
    }

    // 밑에는 검색창 전용

    /**
     * 피부타입 기반 랭킹 조회
     *
     * @param skinType 유저의 피부타입(DRY, OILY, NORMAL 등)
     * @param limit 최대 개수 (기본 10)
     * @return RankingProductResponse 리스트
     */
    public List<RankingProductResponse> getRankingBySkinType(String skinType, int limit) {

        // limit 기본값 처리
        if (limit <= 0) {
            limit = 10; // 검색창은 10개만 필요
        }

        // 캐시에서 호출
        return productRankingMapper.getRankingBySkinType(skinType, limit);
    }


    /**
     * 비로그인 사용자를 위한 전체 TOP10 조회
     *
     * @param limit 최대 10개
     */
    public List<RankingProductResponse> getRankingOverallForSearch(int limit) {

        // limit 기본값 처리
        if (limit <= 0) {
            limit = 10;
        }

        // skinType = 'ALL' 기준 조회
        return productRankingMapper.getRankingOverallForSearch(limit);
    }
}