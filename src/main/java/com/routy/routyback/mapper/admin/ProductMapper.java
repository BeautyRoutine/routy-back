package com.routy.routyback.mapper.admin;

import com.routy.routyback.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    // 전체 조회

    // 단건 조회
    ProductDTO selectById(int prdNo);

    // 상품 등록
    void productInsert(ProductDTO product);

    // 전체 수정
    void productUpdate(ProductDTO product);

    // 삭제
    void productDelete(int prdNo);

    // 재고 수정
    void productUpdateStock(int prdNo, int prdStock);

    // 상태 수정
    void productUpdateStatus(int prdNo, String status);

    // ⭐ 검색 + 페이징 조회
    List<ProductDTO> searchProducts(Map<String, Object> params);

    // ⭐ 검색된 데이터 개수
    int countProducts(Map<String, Object> params);
    
    // 성분 번호 검색
    Integer searchIngredients(String name);
    
    // 성분 매핑
    void mappingIngredients(int prdNo, int ingNo);
    
    // 없는 성분 이름 등록
    void insertIngredients(Map<String, Object> param);
}
