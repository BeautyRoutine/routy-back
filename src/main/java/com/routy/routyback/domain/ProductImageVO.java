package com.routy.routyback.domain;

import lombok.Data;

@Data
public class ProductImageVO {
    private int piNo;       // 이미지 번호
    private int prdNo;      // 상품 번호
    private String piUrl;   // 이미지 경로 
    private String piType;  // 타입 (GALLERY /  DETAIL)
    private int piSort;     // 순서
}