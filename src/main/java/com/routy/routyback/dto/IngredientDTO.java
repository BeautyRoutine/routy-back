package com.routy.routyback.dto;

import java.util.Date;

public class IngredientDTO {
	private int ingNo; // 성분 번호 (PK)
	private String ingName; // 성분 한글 이름
	private String ingEnName; // 성분 영어 이름
	private String ingDesc; // 성분 설명
	private int ingAllergen; // 알레르기 여부 (0/1)
	private int ingDanger; // 주의 성분 여부 (0/1)
	private String ingFunctional; // 기능성 (예: 여드름 완화)
	private Integer ingGrpNo; // 그룹 번호 (nullable)
	private Date ingRegDate; // 등록일 (CURRENT_TIMESTAMP)
	
	public int getIngNo() {
		return ingNo;
	}
	public void setIngNo(int ingNo) {
		this.ingNo = ingNo;
	}
	public String getIngName() {
		return ingName;
	}
	public void setIngName(String ingName) {
		this.ingName = ingName;
	}
	public String getIngEnName() {
		return ingEnName;
	}
	public void setIngEnName(String ingEnName) {
		this.ingEnName = ingEnName;
	}
	public String getIngDesc() {
		return ingDesc;
	}
	public void setIngDesc(String ingDesc) {
		this.ingDesc = ingDesc;
	}
	public int getIngAllergen() {
		return ingAllergen;
	}
	public void setIngAllergen(int ingAllergen) {
		this.ingAllergen = ingAllergen;
	}
	public int getIngDanger() {
		return ingDanger;
	}
	public void setIngDanger(int ingDanger) {
		this.ingDanger = ingDanger;
	}
	public String getIngFunctional() {
		return ingFunctional;
	}
	public void setIngFunctional(String ingFunctional) {
		this.ingFunctional = ingFunctional;
	}
	public Integer getIngGrpNo() {
		return ingGrpNo;
	}
	public void setIngGrpNo(Integer ingGrpNo) {
		this.ingGrpNo = ingGrpNo;
	}
	public Date getIngRegDate() {
		return ingRegDate;
	}
	public void setIngRegDate(Date ingRegDate) {
		this.ingRegDate = ingRegDate;
	}
}
