package com.routy.routyback.service.user;

import com.routy.routyback.dto.RecommendedProductDTO;
import com.routy.routyback.mapper.user.RecommendedProductMapper;
import com.routy.routyback.service.user.IRecommendedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendedProductService implements IRecommendedProductService {

    @Autowired
    private RecommendedProductMapper mapper;

    @Override
    public List<RecommendedProductDTO> getRecommendedProducts(Integer userNo, List<Integer> recentPrdNos) {

        // 1) 비로그인 → 평점 TOP4 단순 추천
        if (userNo == null || recentPrdNos == null || recentPrdNos.isEmpty()) {
            return mapper.recommendByRating(4);
        }

        // 2) 최근 본 상품들의 카테고리 추출
        List<String> categories = mapper.getCategories(recentPrdNos);

        // 추천 리스트
        List<RecommendedProductDTO> result = new ArrayList<>();

        // 3) 카테고리별 1개씩 추천
        for (String cate : categories) {
            RecommendedProductDTO item = mapper.recommendByCategory(cate);
            if (item != null) result.add(item);
        }

        // 4) 4개 미만이면 평점 상위 상품으로 채우기
        int needMore = 4 - result.size();
        if (needMore > 0) {
            List<RecommendedProductDTO> fill = mapper.recommendByRating(needMore);
            result.addAll(fill);
        }

        // 최종 4개로 제한
        return result.stream().limit(4).toList();
    }
}
