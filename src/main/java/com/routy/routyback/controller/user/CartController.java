package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.CartRequestDTO;
import com.routy.routyback.dto.CartResponseDTO;
import com.routy.routyback.mapper.user.UserMapper;
import com.routy.routyback.service.user.CartService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserMapper userMapper;

    /**
     * [Helper 메서드] SecurityContextHolder(토큰)에서 현재 로그인한 사용자의 userId를 꺼내고, DB에서 userNo(PK)를 조회하여
     * 반환합니다.
     */
    private Long getAuthenticatedUserNo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 익명 사용자인 경우 (필터에서 걸러지겠지만 안전장치)
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("인증 정보가 없습니다. 다시 로그인해주세요.");
        }

        String currentUserId = authentication.getName(); // 토큰에 담긴 userId (예: duda28)

        Long userNo = userMapper.findUserNoByUserId(currentUserId);

        if (userNo == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다: " + currentUserId);
        }
        return userNo;
    }

    /**
     * 1. 장바구니 조회 기존: GET /api/cart/list/{userId} 변경: GET /api/cart/list (내 정보는 토큰이 알고 있음)
     */
    @GetMapping("/cart/list")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCart() {
        Long userNo = getAuthenticatedUserNo(); // 토큰에서 유저 식별
        CartResponseDTO cartData = cartService.getCartView(userNo);
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    /**
     * 2. 장바구니 상품 추가 변경: RequestBody에 userId가 있어도 무시하고, 토큰의 주인을 사용함
     */
    @PostMapping("/cart/items")
    public ResponseEntity<ApiResponse<Object>> addItemToCart(
        @RequestBody CartRequestDTO.AddItem requestDTO) {

        Long userNo = getAuthenticatedUserNo(); // 토큰에서 유저 식별

        cartService.addItem(
            userNo,
            requestDTO.getProductId(),
            requestDTO.getQuantity()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 3. 장바구니 수량 변경 변경: 본인 장바구니인지 확인할 필요 없이 토큰 주인 기준으로 처리
     */
    @PatchMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Object>> updateCartItem(
        @PathVariable Long cartItemId,
        @RequestBody CartRequestDTO.UpdateItem requestDTO
    ) {
        Long userNo = getAuthenticatedUserNo(); // 토큰에서 유저 식별

        cartService.updateItem(
            userNo,
            cartItemId,
            requestDTO.getQuantity()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 4. 장바구니 상품 삭제 변경: RequestBody의 userId 무시
     */
    @DeleteMapping("/cart/items")
    public ResponseEntity<ApiResponse<Object>> deleteCartItems(
        @RequestBody CartRequestDTO.DeleteItems requestDTO
    ) {
        Long userNo = getAuthenticatedUserNo(); // 토큰에서 유저 식별
        List<Long> cartItemIds = requestDTO.getCartItemIds();

        cartService.deleteItems(userNo, cartItemIds);

        return ResponseEntity.ok(ApiResponse.success(null));
    }


    // 장바구니 아이템 개수 조회 API
    @GetMapping("/users/{userId}/cart/count")
    public ResponseEntity<ApiResponse<Object>> getCartCount(
        @PathVariable String userId
    ) {
        int count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(
            ApiResponse.success(Map.of("count", count))
        );
    }
}
