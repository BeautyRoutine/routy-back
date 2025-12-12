package com.routy.routyback.controller.user;

import com.routy.routyback.dto.user.SearchHeaderResponse;
import com.routy.routyback.dto.user.RankingProductResponse;
import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.user.ISearchService;
import com.routy.routyback.service.user.ProductRankingService;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * 검색창 API 컨트롤러
 * - 최근 검색어 조회
 * - 피부타입 기반 TOP10 조회
 */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ISearchService searchService;       // 최근 검색어 서비스
    private final ProductRankingService rankingService; // 랭킹 조회 서비스
    private final UserMapper userMapper;

    /**
     * 검색 API
     * - 사용자가 입력한 keyword 로 상품 검색 수행
     * - 상품명 / 설명 등에 keyword 포함된 상품 반환
     */
    @GetMapping("")
    public ApiResponse<List<RankingProductResponse>> searchProducts(@RequestParam String keyword) {

        Long userNo = getCurrentUserNo();

        // 최근 검색어 저장 (로그인 상태일 때만)
        if (userNo != null) {
            searchService.saveSearchKeyword(userNo, keyword);
        }

        // 상품 검색 수행
        List<RankingProductResponse> results =
            searchService.searchProducts(keyword);

        return ApiResponse.success(results);
    }

    /**
     * 검색창 포커스 시 호출되는 API
     * 최근 검색어 + 피부 타입 기반 추천 상품 TOP10을 반환한다.
     */
    @GetMapping("/header")
    public ApiResponse<SearchHeaderResponse> getSearchHeader() {

        Long userNo = getCurrentUserNo();   // 로그인 여부 확인

        List<String> recentKeywords;                 // 최근 검색어 리스트
        List<RankingProductResponse> topProducts;    // 추천 상품 TOP10

        if (userNo != null) { // 로그인 O

            // 유저 최근 검색어
            recentKeywords = searchService.getRecentKeywords(userNo);

            // 유저 피부 타입 조회
            int skinType = searchService.getUserSkinType(userNo);

            // 피부 타입 기반 추천 TOP10
            topProducts = rankingService.getRankingBySkinType(String.valueOf(skinType), 10);

        } else { // 로그인 X

            recentKeywords = List.of(); // FE는 로컬스토리지 사용

            // 전체 인기 TOP10
            topProducts = rankingService.getRankingOverallForSearch(10);
        }

        // DTO 조립
        SearchHeaderResponse response =
            new SearchHeaderResponse(recentKeywords, topProducts);

        return ApiResponse.success(response);
    }

    /**
     * JWT 기반 현재 로그인 사용자 번호 조회
     * - Authentication.principal = userId
     * - userId → userNo 매핑
     */
    private Long getCurrentUserNo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return null; // 비로그인
        }

        String userId = (String) auth.getPrincipal(); // JWT에서 추출된 userId
        var user = userMapper.findByUserId(userId);   // DB 조회

        return user != null ? user.getUserNo() : null;
    }


}