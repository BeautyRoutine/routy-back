package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.RankingProductResponse;

import java.util.List;

/**
 * 상품 랭킹 서비스 인터페이스
 */
public interface IProductRankingService {

    /**
     * 카테고리 + 판매량 기반 랭킹 조회
     *
     * @param category 카테고리명 (null 가능)
     * @param limit 조회 개수 (기본 20)
     * @return 랭킹 데이터 리스트
     */
    List<RankingProductResponse> getCategoryRanking(String category, int limit);
    
}