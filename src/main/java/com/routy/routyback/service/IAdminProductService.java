package com.routy.routyback.service;

import java.util.ArrayList;

import com.routy.routyback.domain.ProductVO;

public interface IAdminProductService {
	
	// 전체 조회
	ArrayList<ProductVO> getAll();
	
	// 특정 상품 조회	
	ProductVO getById(int prdNo);
	/* 상품 crud 기능 */
	
	// 상품 추가
	public void insertProduct();
	// 상품 수정 
	public void updateProduct();
	// 상품 삭제	
	public void deleteProduct();
	// 상품 재고 수정 : 
	public void updateStock();
	// 상품 상태 수정
	public void updateStatus();
	// 상품 페이징 처리 : 
	
	// 상품 랭킹 수정
	public void ranking();
	// 성분 crud 기능
	// 상품 추가
	public void insertIngredient();
	// 성분 수정 
	public void updateIngredient();
	// 성분 삭제	
	public void deleteIngredient();
	// 상품 통계 조회
}
