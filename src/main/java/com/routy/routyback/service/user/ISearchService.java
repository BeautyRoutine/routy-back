package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.RankingProductResponse;
import java.util.List;

public interface ISearchService {

    // 최근 검색어 조회
    List<String> getRecentKeywords(Long userId);

    // 유저 피부타입 조회 (숫자 코드 반환)
    int getUserSkinType(Long userId);

    // 최근 검색어 저장
    void saveSearchKeyword(Long userNo, String keyword);

    // 키워드 기반 상품 검색
    List<RankingProductResponse> searchProducts(String keyword);
}
