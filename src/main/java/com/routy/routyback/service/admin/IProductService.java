package com.routy.routyback.service.admin;

import com.routy.routyback.dto.ProductDTO;

import java.util.Map;

public interface IProductService {

    // 목록 조회(검색 + 페이징)
    Map<String, Object> getList(Map<String, Object> params);

    // 단건 조회
    ProductDTO getById(int prdNo);

    // 등록
    void insertProduct(ProductDTO product);

    // 수정
    void updateProduct(ProductDTO product);

    // 삭제
    void deleteProduct(int prdNo);

    // 재고 수정
    void updateStock(int prdNo, int prdStock);

    // 상태 수정
    void updateStatus(int prdNo, String status);
}
