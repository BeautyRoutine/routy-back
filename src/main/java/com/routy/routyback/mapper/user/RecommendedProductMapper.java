package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.RecommendedProductDTO;

import java.util.List;

public interface RecommendedProductMapper {

    List<RecommendedProductDTO> recommendByRating(int limit);

    RecommendedProductDTO recommendByCategory(String cate);

    List<String> getCategories(List<Integer> prdNos);
}
