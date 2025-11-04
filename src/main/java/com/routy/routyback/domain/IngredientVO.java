package com.routy.routyback.domain;

import java.util.Date;

public class IngredientVO {
	private int ingNo;
	private String ingName;
	private String ingEnName;
	private String ingDesc;
	private int allergen;
	private int ingGrpNo;
	private Date ingRegDate;

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

	public int getAllergen() {
		return allergen;
	}

	public void setAllergen(int allergen) {
		this.allergen = allergen;
	}

	public int getIngGrpNo() {
		return ingGrpNo;
	}

	public void setIngGrpNo(int ingGrpNo) {
		this.ingGrpNo = ingGrpNo;
	}

	public Date getIngRegDate() {
		return ingRegDate;
	}

	public void setIngRegDate(Date ingRegDate) {
		this.ingRegDate = ingRegDate;
	}
}
