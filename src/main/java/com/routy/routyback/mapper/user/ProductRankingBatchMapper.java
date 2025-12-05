package com.routy.routyback.mapper.user;

import org.apache.ibatis.annotations.Mapper;

/**
 * PRODUCT_RANKING_CACHE 테이블을 관리하는 MyBatis Mapper 인터페이스입니다.
 * - 캐시 삭제
 * - 전체 랭킹 INSERT
 * - 카테고리별 랭킹 INSERT
 */
@Mapper
public interface ProductRankingBatchMapper {

    /**
     * 오늘 날짜의 캐시 데이터를 모두 삭제합니다.
     * TRUNC(SYSDATE) 기준으로 삭제됩니다.
     */
    void deleteTodayCache();

    /**
     * 전체 인기 상품 랭킹 데이터를 PRODUCT_RANKING_CACHE 테이블에 저장합니다.
     * 판매량 기준 정렬 후 RANK() OVER 로 순위를 부여합니다.
     */
    void insertOverallRankingCache();

    /**
     * 카테고리별 랭킹 데이터를 PRODUCT_RANKING_CACHE 테이블에 저장합니다.
     */
    void insertCategoryRankingCache();
}