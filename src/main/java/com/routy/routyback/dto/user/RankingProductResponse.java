package com.routy.routyback.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 판매량 + 랭킹(순위) + 카테고리 필터 기반 DTO
 * - PRODUCT_RANKING_CACHE 테이블 조회 결과를 그대로 담아 FE 로 전달합니다.
 */
@Getter
@Setter
public class RankingProductResponse {

    /** 상품 번호 (PRDNO) */
    private Long prdNo;

    /** 상품명 (PRODUCT.PRDNAME) */
    private String prdName;

    /** 판매 가격 (PRODUCT.PRDPRICE) */
    private Integer prdPrice;

    /** 상품 이미지 URL (BE에서 prefix 붙여 full URL로 변환) */
    private String prdImg;

    /** 총 판매량 (CACHE.SALES) */
    private Integer sales;

    /** 캐시에서 계산된 순위 (CACHE.RANK_NO) */
    private Integer rankNo;
    
    /** 피부타입별 긍정반응 갯수 */
    private Integer skin1_cnt;
    private Integer skin2_cnt;
    private Integer skin3_cnt;
    
    /** 별점 갯수 */
    private Double avgRating;
    /** 리뷰 갯수 */
    private Integer reviewCount;
}