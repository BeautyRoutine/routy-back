package com.routy.routyback.service.admin;

import java.util.ArrayList;
import com.routy.routyback.dto.ProductDTO;

public interface IProductService {

    // 전체 목록 조회
    ArrayList<ProductDTO> getAll(String prdName, String prdCompany);


    // 단건 조회
    ProductDTO getById(int prdNo);

    // 상품 등록
    void insertProduct(ProductDTO product);

    // 상품 수정 (전체 정보)
    void updateProduct(ProductDTO product);

    // 상품 삭제
    void deleteProduct(int prdNo);

    // 상품 재고 수정
    void updateStock(int prdNo, int prdStock);

    // 상품 상태 수정 (판매중/품절 등)
    void updateStatus(int prdNo, String status);
}