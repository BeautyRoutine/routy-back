package com.routy.routyback.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.routy.routyback.dto.user.RankingProductResponse;

@Mapper
public interface SearchMapper {

    // 최근 검색어 조회
    List<String> selectRecentKeywords(@Param("userNo") Long userNo);

    // 유저 피부타입 조회 (NUMBER(1) → int)
    Integer selectUserSkinType(@Param("userNo") Long userNo);

    // 최근 검색어 저장
    void insertSearchKeyword(@Param("userNo") Long userNo,
        @Param("keyword") String keyword);

    // 키워드 기반 상품 검색
    List<RankingProductResponse> searchProductsByKeyword(@Param("keyword") String keyword);
}
