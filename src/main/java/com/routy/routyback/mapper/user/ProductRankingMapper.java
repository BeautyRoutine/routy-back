package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.user.RankingProductResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상품 랭킹 관련 Mapper
 * 판매량 기반 랭킹 조회 기능만 담당한다.
 */
@Mapper
public interface ProductRankingMapper {

    /**
     * 판매량 랭킹 조회
     * @param category 필터용 카테고리 (null 허용)
     * @param limit 최대 조회 개수
     */
    List<RankingProductResponse> getCategoryRanking(
        @Param("category") String category,
        @Param("limit") int limit
    );
}