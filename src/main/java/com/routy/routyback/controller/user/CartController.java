package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.CartRequestDTO;
import com.routy.routyback.dto.CartResponseDTO;
import com.routy.routyback.service.user.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private Long getCurrentUserNo() {
        return 1L; // 1번 유저로 하드코딩
    }

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCart() {
        Long userNo = getCurrentUserNo();
        CartResponseDTO cartData = cartService.getCartView(userNo);
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    @PostMapping("/cart/items")
    public ResponseEntity<ApiResponse<Object>> addItemToCart(
        @RequestBody CartRequestDTO.AddItem requestDTO
    ) {
        Long userNo = getCurrentUserNo();
        cartService.addItem(
            userNo,
            requestDTO.getProductId(),
            requestDTO.getQuantity()
        );
        // 성공 신호만 반환
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Object>> updateCartItem(
        @PathVariable Long cartItemId,
        @RequestBody CartRequestDTO.UpdateItem requestDTO
    ) {
        Long userNo = getCurrentUserNo();
        cartService.updateItem(
            userNo,
            cartItemId,
            requestDTO.getQuantity()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/cart/items")
    public ResponseEntity<ApiResponse<Object>> deleteCartItems(
        @RequestBody CartRequestDTO.DeleteItems requestDTO
    ) {
        Long userNo = getCurrentUserNo();
        List<Long> cartItemIds = requestDTO.getCartItemIds();
        cartService.deleteItems(userNo, cartItemIds);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
