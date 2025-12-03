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
    public ApiResponse<List<RecentProductResponse>> getRecentProducts(@PathVariable String userId) {
        return ApiResponse.success(recentProductService.getRecentProducts(userId));
    }
}