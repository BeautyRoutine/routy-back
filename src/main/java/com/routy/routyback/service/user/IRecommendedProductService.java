package com.routy.routyback.service.user;

import com.routy.routyback.dto.RecommendedProductDTO;

import java.util.List;

public interface IRecommendedProductService {
    List<RecommendedProductDTO> getRecommendedProducts(Integer userNo, List<Integer> recentPrdNos);
}
