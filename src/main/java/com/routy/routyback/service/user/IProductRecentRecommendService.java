package com.routy.routyback.service.user;

import com.routy.routyback.common.ApiResponse;

import java.util.List;

public interface IProductRecentRecommendService {
    ApiResponse getRecommendedProducts(Integer userNo, List<Integer> recent, Integer subcate);
}
