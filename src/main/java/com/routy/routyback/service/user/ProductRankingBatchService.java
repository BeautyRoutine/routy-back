package com.routy.routyback.service.user;

import com.routy.routyback.mapper.user.ProductRankingBatchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ProductRankingBatchService 인터페이스의 구현체입니다.
 * - 랭킹 캐시를 갱신하는 실제 로직이 들어있습니다.
 */
@Slf4j // 로그 사용을 위한 어노테이션
@Service // 스프링 빈 등록
@RequiredArgsConstructor // final 필드를 자동 DI
public class ProductRankingBatchService implements IProductRankingBatchService {

    // 배치용 Mapper (DELETE + INSERT)
    private final ProductRankingBatchMapper productRankingBatchMapper;

    /**
     * 랭킹 캐시를 갱신하는 메인 로직입니다.
     * - 오늘 날짜 캐시 삭제
     * - 전체 랭킹 계산 후 INSERT
     * - 카테고리별 랭킹 계산 후 INSERT
     */
    @Override
    public void updateRankingCache() {

        // 1) 오늘자 캐시 삭제
        log.info("[Ranking Batch] 기존 캐시 삭제 시작");
        productRankingBatchMapper.deleteTodayCache();
        log.info("[Ranking Batch] 기존 캐시 삭제 완료");

        // 2) 전체 랭킹 캐시 생성
        log.info("[Ranking Batch] 전체 랭킹 캐시 INSERT 시작");
        productRankingBatchMapper.insertOverallRankingCache();
        log.info("[Ranking Batch] 전체 랭킹 캐시 INSERT 완료");

        // 3) 카테고리별 랭킹 캐시 생성
        log.info("[Ranking Batch] 카테고리별 랭킹 캐시 INSERT 시작");
        productRankingBatchMapper.insertCategoryRankingCache();
        log.info("[Ranking Batch] 카테고리별 랭킹 캐시 INSERT 완료");
    }
}