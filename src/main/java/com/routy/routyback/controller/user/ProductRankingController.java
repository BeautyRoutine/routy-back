package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.RankingProductResponse;
import com.routy.routyback.service.user.IProductRankingService;
import com.routy.routyback.service.user.ProductRankingBatchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 랭킹 조회 컨트롤러
 *
 * - 이제 이 컨트롤러는 “실시간 집계”가 아닌
 * PRODUCT_RANKING_CACHE 테이블에서 조회된 데이터를 그대로 반환합니다.
 *
 * - 매일 자정(00:00) 배치가 캐시를 갱신하므로
 * FE는 매우 빠르고 안정적이며 고정된 랭킹 데이터를 받을 수 있습니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductRankingController {

    // 랭킹 조회 전용 서비스 (캐시 기반)
    private final IProductRankingService productRankingService;
    private final ProductRankingBatchService productRankingBatchService;

    /**
     * 상품 랭킹 조회 API
     *
     * @param category 카테고리 코드
     *                 - null 또는 "0"이면 전체 랭킹 조회
     * @param limit    최대 조회 개수 (기본값 20)
     * @return 상품 랭킹 리스트 (캐시 기반)
     */
    @GetMapping("/ranking")
    public ApiResponse<List<RankingProductResponse>> getCategoryRanking(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer skin,
            @RequestParam(defaultValue = "20") int limit) {

        // 1) 서비스에서 캐시 기반 랭킹 조회
        List<RankingProductResponse> list = productRankingService.getCategoryRanking(category, skin, limit);

        // 3) API 응답 래핑 후 반환
        return ApiResponse.success(list);
    }

    /**
     * 강제 랭킹 캐시 갱신 API (테스트용)
     *
     * - 배치 스케줄러를 기다리지 않고,
     * 관리자가 직접 요청하여 PRODUCT_RANKING_CACHE 를 갱신할 수 있습니다.
     *
     * - 호출 시:
     * 1) 기존 캐시 삭제
     * 2) 전체 랭킹 캐시 INSERT
     * 3) 카테고리별 랭킹 캐시 INSERT
     *
     * - 실제 운영에서는 인증 필요하지만,
     * 개발 단계에서는 편의를 위해 공개해둡니다.
     */
    @GetMapping("/ranking/cache/update")
    public ApiResponse<String> forceUpdateRankingCache() {

        // 1) 캐시 갱신 실행
        productRankingBatchService.updateRankingCache();

        // 2) FE 확인용 결과 메시지 반환
        return ApiResponse.success("랭킹 캐시 갱신 완료");
    }
}