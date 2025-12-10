package com.routy.routyback.dto.user;

import lombok.Data;

/**
 * 최근 본 상품 응답 DTO
 */
@Data
public class RecentProductResponse {

    private Long prdNo;     // 상품 번호
    private String prdName;       // 상품명
    private String prdImg;       // 대표 이미지 URL
    private Long prdSubCate;
}