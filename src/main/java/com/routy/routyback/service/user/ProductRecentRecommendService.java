package com.routy.routyback.service.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.ProductRecentRecommendDTO;
import com.routy.routyback.mapper.user.ProductRecentRecommendMapper;
import com.routy.routyback.mapper.user.RecentProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recent")
public class ProductRecentRecommendService implements IProductRecentRecommendService {

    @Autowired
    ProductRecentRecommendMapper mapper;

    @Autowired
    RecentProductMapper recentMapper;

    @Override
    public ApiResponse getRecommendedProducts(Integer userNo, Integer subcate, String userId) {

        // 1) userNo 없으면 userId → userNo 조회
        if (userNo == null && userId != null) {
            Long temp = recentMapper.getUserNoByUserId(userId);
            if (temp != null) {
                userNo = temp.intValue();   // Integer로 변환
            }
        }

        if (userNo == null) {
            return ApiResponse.success(List.of());
        }

        // 2) subcate 없으면 → 최근 본 상품에서 최신 1개 subcate 가져오기
        if (subcate == null) {
            Integer latestSub = mapper.findLatestSubcate(userNo);
            subcate = latestSub;
        }

        if (subcate == null) {
            return ApiResponse.success(List.of());
        }

        // 3) 추천 상품 조회
        List<ProductRecentRecommendDTO> list =
                mapper.recommendByLatestSubcate(userNo, subcate);

        return ApiResponse.success(list);
    }
}


