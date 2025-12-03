package com.routy.routyback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailDTO {
	private int prdNo;
	private String prdVolume_text; // 용량 또는 중량(예: 50ml)
	private String prdSpec; // 제품 주요 사양(피부 타입 등)
	private String prdExpire; // 사용기한 또는 개봉 후 사용기간
	private String prdUsage; // 사용방법
    private String prdManuf; // 제조자 및 제조판매업자
    private String prdOrigin; // 제조국(원산지)
    private String prdFda; // 식약처 심사 필 여부
    private String prdIngredients; // 주요성분
    private String prdCaution; // 사용 시 주의사항
    private String prdQuality; // 품질보증기준
    private String prdCs_phone; // 소비자 상담 전화번호

}
