package com.routy.routyback.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderPrdDTO {
	private int ppMapNo;
	private int odNo;
	private int prdNo;
	private int ppMapStock;
	private int ppMapPrice;
	
	private String prdName;
	private int prdVolume;
	private int prdPrice;
	private String prdCompany;
	private int prdMainCate;
	private String mainCateStr;
	private int prdSubCate;
	private String subCateStr;
	private String prdImg;

}
