package com.routy.routyback.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.routy.routyback.domain.ProductVO;

@Service
public class ProductService implements IProductService {

	@Override
	public ArrayList<ProductVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductVO getById(int prdNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStock(int prdNo, int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStatus(int prdNo, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertProduct(ProductVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(ProductVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProduct(int prdNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRanking() {
		// TODO Auto-generated method stub

	}

}