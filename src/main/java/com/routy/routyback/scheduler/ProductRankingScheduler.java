package com.routy.routyback.scheduler;

import com.routy.routyback.service.user.ProductRankingBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 상품 랭킹 캐시를 주기적으로 갱신하는 스케줄러 클래스입니다.
 *
 * - 매일 정해진 시간(현재 설정: 서버 기준 00:00)에 한 번 실행됩니다.
 * - 실행 시, ProductRankingBatchService 를 호출하여
 *   PRODUCT_RANKING_CACHE 테이블을 최신 상태로 다시 계산합니다.
 */
@Slf4j                    // 로그 출력용 어노테이션 (log.info(), log.error() 등 사용)
@Component                // 스프링 빈 등록 (Scheduling 에서 자동으로 감지하여 실행)
@RequiredArgsConstructor  // final 필드를 파라미터로 받는 생성자를 자동 생성
public class ProductRankingScheduler {

    /**
     * 실제 랭킹 캐시를 생성/갱신하는 비즈니스 로직을 담당하는 서비스입니다.
     * - 배치 로직(DELETE + INSERT)을 이 서비스 안에 모아두고
     *   스케줄러에서는 "언제 호출할지"만 책임집니다.
     */
    private final ProductRankingBatchService productRankingBatchService;

    /**
     * 매일 서버 기준 00:00 에 실행되는 배치 메서드입니다.
     *
     * - cron = "0 0 0 * * ?" 의 의미:
     *   초   분   시  일  월  요일
     *   0   0   0   *   *   ?  → 매일 00시 00분 00초
     *
     * - 이 메서드는 스케줄러에 의해 자동 호출되며,
     *   내부에서 랭킹 캐시 테이블을 전부 다시 계산합니다.
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void updateDailyRankingCache() {
        // 1) 배치 시작 로그
        log.info("[ProductRankingScheduler] 매일 07:00 상품 랭킹 캐시 갱신 배치 시작");

        // 2) 랭킹 캐시 갱신 비즈니스 로직 호출
        productRankingBatchService.updateRankingCache();

        // 2) 피부타입 기반 랭킹 캐시 갱신
        productRankingBatchService.updateSkinTypeRankingCache();

        // 3) 배치 종료 로그
        log.info("[ProductRankingScheduler] 상품 랭킹 캐시 갱신 배치 종료");
    }
}
