package com.routy.routyback.service.user;

import com.routy.routyback.common.ApiResponse;

import java.util.List;

public interface IProductRecentRecommendService {
    ApiResponse getRecommendedProducts(Integer userNo, Integer subcate, String userId);
}
