package com.routy.routyback.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.routy.routyback.dto.ProductDTO;

@Mapper
public interface ProductMapper {
	ArrayList<ProductDTO> getAll();

	ProductDTO selectById(int prdNo);

	void insertProduct(ProductDTO product);

	void updateProduct(ProductDTO product);

	void deleteProduct(int prdNo);

	void updateStock(@Param("prdNo") int prdNo, @Param("amount") int amount);

	void updateStatus(@Param("prdNo") int prdNo, @Param("status") String status);

	void updateRanking();
}
