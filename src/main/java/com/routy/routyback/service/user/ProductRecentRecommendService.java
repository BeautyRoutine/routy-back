package com.routy.routyback.service.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.ProductRecentRecommendDTO;
import com.routy.routyback.mapper.user.ProductRecentRecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recent")
public class ProductRecentRecommendService implements IProductRecentRecommendService {

    @Autowired
    ProductRecentRecommendMapper mapper;

    @Override
    public ApiResponse getRecommendedProducts(Integer userNo, List<Integer> recent, Integer subcate) {

        List<ProductRecentRecommendDTO> list =
                mapper.selectRecommendedProducts(userNo, recent, subcate);

        return ApiResponse.success(list);
    }
}

