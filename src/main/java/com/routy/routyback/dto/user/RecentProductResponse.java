package com.routy.routyback.dto.user.mypage;

import lombok.Data;

/**
 * 최근 본 상품 응답 DTO
 */
@Data
public class RecentProductResponse {

    private Long productId;     // 상품 번호
    private String title;       // 상품명
    private String image;       // 대표 이미지 URL
}