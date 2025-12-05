package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.ProductUserDTO;
import com.routy.routyback.service.user.IProductRecentRecommendService;
import com.routy.routyback.service.user.IProductUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductUserController {

    @Autowired
    @Qualifier("productUserService")
    private IProductUserService service;

    @Autowired
    @Qualifier("recent")
    private IProductRecentRecommendService recommendService;

    @GetMapping("/list/skin_type")
    public ApiResponse productSkinBased(@RequestParam Map<String, Object> param) {
        return service.productSkinBased(param);
    }

    @GetMapping("/list/skin_cate")
    public ApiResponse productAllSkinCate(@RequestParam Map<String, Object> param) {
        return service.productAllSkinCate(param);
    }

    @GetMapping("/list/skin_commend")
    public ApiResponse productAllSkinCommend(@RequestParam Map<String, Object> param) {
        return service.productAllSkinCommend(param);
    }

    @GetMapping("/list/fallback")
    public ApiResponse getFallbackProducts(@RequestParam int limit) {
        return service.getFallbackProducts(limit);
    }


    @GetMapping("/list/recent")
    public ApiResponse getRecommendedProducts(
            @RequestParam(required = false) Integer userNo,
            @RequestParam(required = false) List<Integer> recent,
            @RequestParam(required = false) Integer subcate
    ) {
        return recommendService.getRecommendedProducts(userNo, recent, subcate);
    }

    @GetMapping("/{prdNo}")
    public ApiResponse<ProductUserDTO> productDetailView(@PathVariable int prdNo) {
        try {
            ProductUserDTO dto = service.productDetailView(prdNo);
            if (dto == null) {
                throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
            }
            return ApiResponse.success(dto);
        } catch (Exception e) {
            return ApiResponse.fromException(e);
        }
    }
}
