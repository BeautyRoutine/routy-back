package com.routy.routyback.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.routy.routyback.domain.ProductVO;

@Service
public class AdminProductService implements IAdminProductService {
	
	@Override
	public ArrayList<ProductVO> getAll() {
		// 전체 조회
		return null;
	}

	@Override
	public ProductVO getById(int prdNo) {
		// 특정 상품 조회
		return null;
	}

	@Override
	public void insertProduct() {
		// 상품 추가
		
	}

	@Override
	public void updateProduct() {
		// 상품 수정
		
	}

	@Override
	public void deleteProduct() {
		// 상품 삭제
		
	}

	@Override
	public void updateStock() {
		// 상품 수량 수정
		
	}

	@Override
	public void updateStatus() {
		// 상품 상태 수정
		
	}

	@Override
	public void ranking() {
		// 상품 랭킹 수정
		
	}

	@Override
	public void insertIngredient() {
		// 상품 성분 추가
		
	}

	@Override
	public void updateIngredient() {
		// 상품 성분 수정
		
	}

	@Override
	public void deleteIngredient() {
		// 상품 성분 삭제
		
	}

	
}
