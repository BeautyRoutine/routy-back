package com.routy.routyback.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.routy.routyback.domain.ProductVO;

@Service
public class ProductService implements IProductService {

	@Override
	public ArrayList<ProductVO> getAll() {
		// TODO 상품 조회
		return null;
	}

	@Override
	public ProductVO getById(int prdNo) {
		// TODO id로 상품 검색
		return null;
	}

	@Override
	public void updateStock(int prdNo, int amount) {
		// TODO 상품 수량 로직

	}

	@Override
	public void updateStatus(int prdNo, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertProduct(ProductVO vo) {
		// TODO 상품 추가 로직

	}

	@Override
	public void updateProduct(ProductVO vo) {
		// TODO 상품 업데이트 로직
	}

	@Override
	public void deleteProduct(int prdNo) {
		// TODO 상품 삭제 로직

	}

	@Override
	public void updateRanking() {
		// TODO 랭킹 업데이트에 대한 로직

	}

}