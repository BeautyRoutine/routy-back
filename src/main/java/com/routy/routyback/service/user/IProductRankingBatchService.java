package com.routy.routyback.service.user;

/**
 * 매일 자정에 실행되는 상품 랭킹 캐시(batch) 작업을 담당하는 서비스 인터페이스입니다.
 * - 캐시 초기화(삭제)
 * - 전체 랭킹 계산 후 캐시에 저장
 * - 카테고리별 랭킹 계산 후 캐시에 저장
 */
public interface IProductRankingBatchService {

    /**
     * 전체 랭킹 캐시를 업데이트하는 주요 배치 메서드입니다.
     * - 오늘 데이터 삭제
     * - 전체 랭킹 INSERT
     * - 카테고리별 랭킹 INSERT
     */
    void updateRankingCache();
}