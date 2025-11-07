package com.routy.routyback.service.admin;

import java.util.ArrayList;

import com.routy.routyback.dto.ProductDTO;

public interface IProductService {

	ArrayList<ProductDTO> getAll();

	ProductDTO getById(int prdNo);

	void updateStock(int prdNo, int amount);

	void updateStatus(int prdNo, String status);

	void insertProduct(ProductDTO product);

	void updateProduct(ProductDTO product);

	void deleteProduct(int prdNo);
}