package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.RecentProductResponse;
import com.routy.routyback.service.user.mypage.IRecentProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 최근 본 상품 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/recent-products")
public class UserRecentProductController {

    private final IRecentProductService recentProductService;

    @GetMapping
    public ApiResponse<List<RecentProductResponse>> getRecentProducts(@PathVariable("userId") String userId) {
        Long userNo = recentProductService.getUserNoByUserId(userId);
        return ApiResponse.success(recentProductService.getRecentProducts(userNo));
    }

    @PostMapping
    public ApiResponse<Void> addRecentProduct(
        @PathVariable("userId") String userId,
        @RequestParam Long prdNo,
        @RequestParam(value = "prdSubCate", required = true) Long prdSubCate) {

        Long userNo = recentProductService.getUserNoByUserId(userId);
        recentProductService.addRecentProduct(userNo, prdNo, prdSubCate);
        return ApiResponse.success(null);
    }
}