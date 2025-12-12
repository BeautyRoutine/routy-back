package com.routy.routyback.dto;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUserDTO {
	private int prdNo;
	private String prdName;
	private int prdPrice;
	private int prdVolume;
	private String prdCompany;
	private String prdMainCate;
	private String prdSubCate;
    
	// 리스트용 썸네일 이미지 --남겨놓을지 유지할지 못정함
	private String prdImg;
	
	//상세페이지용 이미지 map
	 private Map<String, List<String>> images; 
	 
	 
	private String prdDesc;
	private int cnt;

    // 추천을 위한 부분.
    private Double avgRating;
    private Integer reviewCount;

    private String prdVolume_text; // 용량 텍스트
    private String prdSpec;        // 주요 사양
    private String prdExpire;      // 사용기한
    private String prdUsage;       // 사용방법
    private String prdManuf;       // 제조사
    private String prdOrigin;      // 원산지
    private String prdFda;         // 식약처 심사 여부
    private String prdIngredients; // 전성분(텍스트)
    private String prdCaution;     // 주의사항
    private String prdQuality;     // 품질보증기준
    private String prdCs_phone;    // AS 전화번호
}
