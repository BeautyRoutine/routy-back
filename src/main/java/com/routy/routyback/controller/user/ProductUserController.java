package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.ProductUserDTO;
import com.routy.routyback.dto.RecommendedProductDTO;
import com.routy.routyback.service.user.IProductUserService;

import java.util.List;
import java.util.Map;

import com.routy.routyback.service.user.IRecommendedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // restapi
@RequestMapping("/api/products") // 기본 주소
public class ProductUserController {

    // DI 주입
    @Autowired
    @Qualifier("productUserService")
    IProductUserService service;  // 서비스 인터페이스 주입

    @Autowired
    IRecommendedProductService recommendedProductService;

    @GetMapping("/{prdNo}")
    public ApiResponse<ProductUserDTO> productDetailView(@PathVariable int prdNo) {
        try {
            ProductUserDTO dto = service.productDetailView(prdNo);
            
            if (dto == null) {
                // null이면 예외처리
                throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
            }
            return ApiResponse.success(dto); // 성공 시 data에 DTO를 담아 반환
            
        } catch (Exception e) {
            return ApiResponse.fromException(e); // 실패 시 에러 메시지 반환
        }
    }

    @GetMapping("/list/skin_cate")
    public ApiResponse productAllSkinCate(@RequestParam Map<String, Object> param) { // 피부타입별 추천 제품목록
    	return service.productAllSkinCate(param);
    }
    @GetMapping("/list/skin_commend")
    public ApiResponse productAllSkinCommend(@RequestParam Map<String, Object> param) { // 당신을 위한 맞춤 추천
    	return service.productAllSkinCommend(param);
    }

    @GetMapping("/recommend")
    public List<RecommendedProductDTO> getRecommendedProducts(
            @RequestParam(required = false) Integer userNo,
            @RequestParam(required = false) List<Integer> recent
    ) {
        return recommendedProductService.getRecommendedProducts(userNo, recent);
    }

}
