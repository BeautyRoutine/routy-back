package com.routy.routyback.dto;

import lombok.Data;

@Data
public class RecommendedProductDTO {

    private int prdNo;
    private String prdName;
    private String prdCompany;
    private String prdImg;

    // 추천 계산 필드
    private Double avgRating;     // 리뷰 평균
    private Integer reviewCount;  // 리뷰 개수

}

