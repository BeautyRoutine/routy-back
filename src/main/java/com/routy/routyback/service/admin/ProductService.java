package com.routy.routyback.service.admin;

import com.routy.routyback.dto.ProductDTO;
import com.routy.routyback.dto.ProductDetailDTO;
import com.routy.routyback.mapper.admin.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Map<String, Object> getList(Map<String, Object> params) {

        // page 값 꺼내기
        int page = (int) params.getOrDefault("page", 1);
        int pageSize = 10;

        // start, end 자동 계산
        params.put("start", (page - 1) * pageSize + 1);
        params.put("end", page * pageSize);

        // 목록 조회
        List<ProductDTO> list = productMapper.searchProducts(params);
        int total = productMapper.countProducts(params);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);

        return result;
    }

    @Override
    public ProductDTO getById(int prdNo) {
        return productMapper.selectById(prdNo);
    }

    @Override
    public String insertProduct(ProductDTO prdDto, ProductDetailDTO prdDetDto) { //상품 추가
    	// 상품정보 등록
    	productMapper.productInsert(prdDto);
    	int prdNo = prdDto.getPrdNo();
    	
    	// 상품 디테일정보 등록
    	prdDetDto.setPrdNo(prdNo);
    	productMapper.productDetailInsert(prdDetDto);
    	
        // 성분 자동 매핑
        // 1. 파싱
    	String[] ingArray = prdDetDto.getPrdIngredients().split(";");
    	// 2. 매핑 실패 시 mapper로 넘길 param
    	Map<String, Object> param = new HashMap<>();
    	// 3. 찾고 매핑하고 실패 시 성분 등록 후 재시도
    	for (String ingName : ingArray) {
    		ingName = ingName.trim(); // 공백 제거
            Integer ingNo = productMapper.searchIngredients(ingName);

            if (ingNo == null) {
            	param.put("ingName", ingName);
            	productMapper.insertIngredients(param);
            	Integer tempIngNo = (Integer) param.get("ingNo");
            	productMapper.mappingIngredients(prdNo, tempIngNo);
            } else {
            	productMapper.mappingIngredients(prdNo, ingNo);
            }
        }
    	
        return "[prdNo:" + prdNo + "] 등록 성공";
    }

    @Override
    public void updateProduct(ProductDTO product) {
        productMapper.productUpdate(product);
    }

    @Override
    @Transactional
    public void deleteProduct(int prdNo) {
        productMapper.deleteProductDetail(prdNo);
        productMapper.deleteProductIngredientMapping(prdNo); // 1) 매핑 삭제
        productMapper.productDelete(prdNo);                   // 2) 상품 삭제
    }


    @Override
    @Transactional
    public void updateStock(int prdNo, int stock) {
        ProductDTO dto = productMapper.selectById(prdNo);
        if (dto == null) {
            throw new RuntimeException("상품이 존재하지 않습니다.");
        }

        int updatedStock = dto.getPrdStock() + stock;
        if (updatedStock < 0) {
            throw new RuntimeException("재고 부족");
        }

        productMapper.productUpdateStock(prdNo, updatedStock);
    }

    @Override
    public void updateStatus(int prdNo, String status) {
        productMapper.productUpdateStatus(prdNo, status);
    }
}
