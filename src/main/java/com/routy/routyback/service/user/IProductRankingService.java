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
    List<RankingProductResponse> getCategoryRanking(String category, Integer skin, int limit);

    /**
     * 피부 타입 기반 랭킹 조회 (검색창 전용)
     *
     * @param skinType 피부 타입 (DRY, OILY, NORMAL 등)
     * @param limit 조회 개수 (기본 10)
     * @return 랭킹 데이터 리스트
     */
    List<RankingProductResponse> getRankingBySkinType(String skinType, int limit);

    /**
     * 전체 TOP 랭킹 조회 (검색창 전용)
     *
     * @param limit 조회 개수 (기본 10)
     * @return 랭킹 데이터 리스트
     */
    List<RankingProductResponse> getRankingOverallForSearch(int limit);
}