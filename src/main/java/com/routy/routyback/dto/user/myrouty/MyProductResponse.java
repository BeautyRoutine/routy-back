package com.routy.routyback.dto.user.myrouty;   // My Routy 전용 DTO 패키지

import lombok.Data;

@Data
public class MyProductResponse {                // 내 제품(구매 인증된 제품) 한 건을 표현하는 DTO

    private Long prdNo;                         // 상품 번호(PRDNO)
    private String prdName;                     // 상품 이름(PRDNM)
    private String prdImg;                      // 상품 이미지 경로(PRDIMG)
    private Integer mainCategory; // 메인 카테고리 (PRDMAINcate)
    private Integer qty;                        // 구매 수량(PPMAPSTOCK)
    private Integer buyPrice;                   // 구매 당시 가격(PPMAPPRICE)
    private String buyDate;                     // 주문 일자(문자열로 포맷팅된 날짜)
}