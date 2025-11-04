package com.routy.routyback.service;

import java.util.ArrayList;

import com.routy.routyback.domain.ProductVO;

public interface IProductService {
	// ✅ 사용자/관리자 공통 기능
	ArrayList<ProductVO> getAll(); // 전체 조회

	ProductVO getById(int prdNo); // 특정 상품 조회

	void updateStock(int prdNo, int amount); // 재고 변경

	void updateStatus(int prdNo, String status); // 상태 변경 (ex. 판매중/품절)

	// ✅ 관리자 전용 기능
	void insertProduct(ProductVO vo);

	void updateProduct(ProductVO vo);

	void deleteProduct(int prdNo);

	// ✅ 상품 랭킹
	void updateRanking(); // 또는 void updateRanking(List<ProductVO> topN);
}
