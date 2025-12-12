package com.routy.routyback.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 검색창 헤더 요약 응답 DTO
 * - 최근 검색어
 * - 피부 타입 기반 추천 상품 리스트
 */
@Data
@AllArgsConstructor      // 모든 필드를 받는 생성자 생성
@NoArgsConstructor       // 기본 생성자 생성
public class SearchHeaderResponse {

    // 최근 검색어 리스트 (최대 10개)
    private List<String> recentKeywords;

    // 피부 타입 기반 추천 상품 목록
    // ProductRankingService 에서 사용하는 RankingProductResponse 재사용
    private List<RankingProductResponse> topProductsBySimilarSkinType;
}
