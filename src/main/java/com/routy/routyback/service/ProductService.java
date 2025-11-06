package com.routy.routyback.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routy.routyback.dto.ProductDTO;
import com.routy.routyback.mapper.admin.ProductMapper;

@Service
public class ProductService implements IProductService {

	@Autowired
	ProductMapper productMapper;

	@Override
	public ArrayList<ProductDTO> getAll() {
		return productMapper.getAll();
	}

	@Override
	public ProductDTO getById(int prdNo) {
		return productMapper.selectById(prdNo);
	}

	@Override
	@Transactional
	public void updateStock(int prdNo, int amount) {
		ProductDTO currentProduct = productMapper.selectById(prdNo);

		if (currentProduct == null) {
			throw new IllegalArgumentException("상품 번호 " + prdNo + "에 해당하는 상품이 없습니다.");
		}

		int newStock = currentProduct.getPrdStock() + amount;

		if (newStock < 0) {
			throw new RuntimeException("상품 [" + currentProduct.getPrdName() + "]의 재고가 부족합니다.");
		}

		productMapper.productUpdateStock(prdNo, newStock);
	}

	@Override
	public void updateStatus(int prdNo, String status) {
		productMapper.productUpdateStatus(prdNo, status);
	}

	@Override
	public void insertProduct(ProductDTO product) {
		productMapper.productInsert(product);
	}

	@Override
	public void updateProduct(ProductDTO product) {
		productMapper.productUpdate(product);
	}

	@Override
	public void deleteProduct(int prdNo) {
		productMapper.productDelete(prdNo);
	}
}