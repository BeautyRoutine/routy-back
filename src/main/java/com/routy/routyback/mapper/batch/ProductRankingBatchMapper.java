package com.routy.routyback.mapper.batch;

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

    /**
     * 오늘 날짜의 피부타입 기반 캐시 데이터를 삭제합니다.
     * (SKIN_TYPE 컬럼이 NOT NULL 인 데이터만 삭제)
     */
    void deleteTodaySkinTypeCache();

    /**
     * VIEWED_PRODUCT 로그 기반으로 피부타입별 랭킹 캐시를 생성합니다.
     * - USERS.USERSKIN 기준 그룹핑
     * - 각 피부타입별 TOP10 조회 후 PRODUCT_RANKING_CACHE 에 INSERT
     */
    void insertSkinTypeRankingCache();
}