package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.RankingProductResponse;
import com.routy.routyback.mapper.user.SearchMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 검색 관련 서비스
 * - 최근 검색어 조회
 * - 유저 피부 타입 조회
 */
@Service // 비즈니스 로직 처리 역할
@RequiredArgsConstructor // final 필드 자동 주입
public class SearchService implements ISearchService {

    private final SearchMapper searchMapper; // 검색 관련 매퍼

    /**
     * 유저의 최근 검색어 가져오기
     */
    public List<String> getRecentKeywords(Long userId) {
        return searchMapper.selectRecentKeywords(userId); // XML 쿼리 호출
    }

    /**
     * 유저의 피부 타입 가져오기
     */
    public int getUserSkinType(Long userId) {
        Integer skinType = searchMapper.selectUserSkinType(userId); // XML 쿼리 호출
        return skinType != null ? skinType : 6; // 6 = 선택안함 기본값
    }

    /**
     * 최근 검색어 저장
     */
    @Override
    public void saveSearchKeyword(Long userNo, String keyword) {
        searchMapper.insertSearchKeyword(userNo, keyword);
    }

    /**
     * 키워드 기반 상품 검색
     */
    @Override
    public List<RankingProductResponse> searchProducts(String keyword) {
        return searchMapper.searchProductsByKeyword(keyword);
    }
}