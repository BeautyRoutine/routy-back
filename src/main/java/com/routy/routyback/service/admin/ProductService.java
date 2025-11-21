package com.routy.routyback.service.admin;

import com.routy.routyback.dto.ProductDTO;
import com.routy.routyback.mapper.admin.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
