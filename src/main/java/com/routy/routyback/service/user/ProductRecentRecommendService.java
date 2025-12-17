package com.routy.routyback.service.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.ProductRecentRecommendDTO;
import com.routy.routyback.mapper.user.ProductRecentRecommendMapper;
import com.routy.routyback.mapper.user.RecentProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("recent")
public class ProductRecentRecommendService implements IProductRecentRecommendService {

    @Autowired
    private ProductRecentRecommendMapper mapper;

    @Autowired
    private RecentProductMapper recentMapper;

    @Override
    public ApiResponse getRecommendedProducts(Integer userNo, Integer subcate, String userId) {

        /* =================================================
         * 1) userNo 보정 (userId → userNo)
         * ================================================= */
        if (userNo == null && userId != null) {
            Long temp = recentMapper.getUserNoByUserId(userId);
            if (temp != null) {
                userNo = temp.intValue();
            }
        }

        if (userNo == null) {
            return ApiResponse.success(Collections.emptyList());
        }

        /* =================================================
         * 2) subCate 보정 (최근 본 상품 기준)
         * ================================================= */
        if (subcate == null) {
            subcate = mapper.findLatestSubcate(userNo);
        }

        if (subcate == null) {
            return ApiResponse.success(Collections.emptyList());
        }

        /* =================================================
         * 3) 1차 추천 : 최근 본 subCate 기준
         * ================================================= */
        List<ProductRecentRecommendDTO> result =
                mapper.recommendByLatestSubcate(userNo, subcate);

        /* =================================================
         * 4) 정책 변경 핵심
         *    - 3개 이하일 경우 mainCate로 보충
         * ================================================= */
        if (result.size() < 4) {

            int need = 4 - result.size();

            // 최근 본 상품의 mainCate 조회
            Integer maincate = mapper.findLatestMaincate(userNo);
            if (maincate != null && need > 0) {

                // 이미 추천된 상품 번호
                List<Integer> excludePrdNos = result.stream()
                        .map(ProductRecentRecommendDTO::getPrdNo)
                        .collect(Collectors.toList());

                List<ProductRecentRecommendDTO> supplement =
                        mapper.recommendByMaincateSupplement(
                                userNo,
                                maincate,
                                excludePrdNos,
                                need
                        );

                result.addAll(supplement);
            }
        }

        /* =================================================
         * 5) 최대 4개 보장 (안전장치)
         * ================================================= */
        if (result.size() > 4) {
            result = result.subList(0, 4);
        }

        return ApiResponse.success(result);
    }
}
