package com.routy.routyback.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.routy.routyback.dto.ProductDTO;

@Mapper
public interface ProductMapper {
    ArrayList<ProductDTO> getAll(
            @Param("prdName") String prdName,
            @Param("prdCompany") String prdCompany
    );

    ProductDTO selectById(int prdNo);

    void productInsert(ProductDTO product);

    void productUpdate(ProductDTO product);

    void productDelete(int prdNo);

    void productUpdateStock(@Param("prdNo") int prdNo, @Param("prdStock") int prdStock);

    void productUpdateStatus(@Param("prdNo") int prdNo, @Param("status") String status);

}