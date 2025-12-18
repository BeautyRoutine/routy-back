package com.routy.routyback.dto.user.mypage;

import lombok.Data;

/**
 * 나의 리뷰 목록 조회 응답 DTO
 * - 리스트 화면에서 사용하는 간단 정보
 */
@Data
public class UserReviewResponse {

    private Long reviewId;      // 리뷰 번호 (REVNO)
    private Long productId;     // 상품 번호 (PRDNO)
    private String productName; // 상품명
    private int rating;         // 별점
    private String content;     // 리뷰 본문 (CONTENT)
    private String createdAt;   // 작성일 (yyyy-MM-dd)
    
    private String revMainImg;
}