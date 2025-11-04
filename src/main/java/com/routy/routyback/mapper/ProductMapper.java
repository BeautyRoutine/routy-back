package com.routy.routyback.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.routy.routyback.domain.ProductVO;

@Mapper
public interface ProductMapper {
	ArrayList<ProductVO> getAll();

	ProductVO selectById(int prdNo);

	void insertProduct(ProductVO product);

	void updateProduct(ProductVO product);

	void deleteProduct(int prdNo);

	void updateStock(@Param("prdNo") int prdNo, @Param("amount") int amount);

	void updateStatus(@Param("prdNo") int prdNo, @Param("status") String status);

	void updateRanking();
}
