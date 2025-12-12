package com.routy.routyback.service.user;

/**
 * 매일 자정에 실행되는 상품 랭킹 캐시(batch) 작업을 담당하는 서비스 인터페이스입니다.
 * - 캐시 초기화(삭제)
 * - 전체 랭킹 계산 후 캐시에 저장
 * - 카테고리별 랭킹 계산 후 캐시에 저장
 */
public interface IProductRankingBatchService {

    /**
     * 피부 타입 기반 랭킹 캐시를 업데이트하는 배치 메서드입니다.
     * - VIEWED_PRODUCT 로그 기반으로 skinType 그룹별 조회수 집계
     * - skinType별 TOP10 상품 캐시에 저장
     * - ALL(전체) 기준 TOP10 캐시도 함께 저장
     */
    void updateSkinTypeRankingCache();

    /**
     * 전체 랭킹 캐시를 업데이트하는 주요 배치 메서드입니다.
     * - 오늘 데이터 삭제
     * - 전체 랭킹 INSERT
     * - 카테고리별 랭킹 INSERT
     */
    void updateRankingCache();
}