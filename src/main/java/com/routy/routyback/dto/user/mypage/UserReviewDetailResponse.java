package com.routy.routyback.dto.user.mypage;

import lombok.Data;

import java.util.List;

/**
 * 나의 리뷰 상세 조회 응답 DTO
 */
@Data
public class UserReviewDetailResponse {

    private Long reviewId;          // 리뷰 번호
    private Long productId;         // 상품 번호
    private String productName;     // 상품명
    private String brandName;       // 브랜드명
    private int rating;             // 별점
    private String good;            // 장점
    private String bad;             // 단점
    private String content;         // 리뷰 본문 (CONTENT)
    private String createdAt;       // 작성일 (yyyy-MM-dd HH:mm:ss)
    private Integer revRank;        // 리뷰 랭크 (선택)
    private Double revTrustScore;   // 신뢰도 점수 (선택)
    private String revTrustRank;    // 신뢰도 등급 (선택)
    private List<String> images;    // 리뷰 이미지 URL 목록
}