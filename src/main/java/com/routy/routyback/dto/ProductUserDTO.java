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

}
