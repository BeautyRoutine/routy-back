package com.routy.routyback.domain;

import java.util.Date;

public class ProductVO {
	private int prdNo;
	private String prdName;
	private int prdPrice;
	private int prdVolume;
	private String prdCompany;
	private int prdStock;
	private int prdMainCate;
	private int prdSubCate;
	private String prdImg;
	private String prdDesc;
	private Date prdUpdate;
	private Date prdRegdate;

	public int getPrdNo() {
		return prdNo;
	}

	public void setPrdNo(int prdNo) {
		this.prdNo = prdNo;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public int getPrdPrice() {
		return prdPrice;
	}

	public void setPrdPrice(int prdPrice) {
		this.prdPrice = prdPrice;
	}

	public int getPrdVolume() {
		return prdVolume;
	}

	public void setPrdVolume(int prdVolume) {
		this.prdVolume = prdVolume;
	}

	public String getPrdCompany() {
		return prdCompany;
	}

	public void setPrdCompany(String prdCompany) {
		this.prdCompany = prdCompany;
	}

	public int getPrdStock() {
		return prdStock;
	}

	public void setPrdStock(int prdStock) {
		this.prdStock = prdStock;
	}

	public int getPrdMainCate() {
		return prdMainCate;
	}

	public void setPrdMainCate(int prdMainCate) {
		this.prdMainCate = prdMainCate;
	}

	public int getPrdSubCate() {
		return prdSubCate;
	}

	public void setPrdSubCate(int prdSubCate) {
		this.prdSubCate = prdSubCate;
	}

	public String getPrdImg() {
		return prdImg;
	}

	public void setPrdImg(String prdImg) {
		this.prdImg = prdImg;
	}

	public String getPrdDesc() {
		return prdDesc;
	}

	public void setPrdDesc(String prdDesc) {
		this.prdDesc = prdDesc;
	}

	public Date getPrdUpdate() {
		return prdUpdate;
	}

	public void setPrdUpdate(Date prdUpdate) {
		this.prdUpdate = prdUpdate;
	}

	public Date getPrdRegdate() {
		return prdRegdate;
	}

	public void setPrdRegdate(Date prdRegdate) {
		this.prdRegdate = prdRegdate;
	}
}
