package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.CartRequestDTO;
import com.routy.routyback.dto.CartResponseDTO;
import com.routy.routyback.service.user.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private Long getCurrentUserNo(){
        return 1L; // 1번 유저로 하드코딩
    }

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCart(){
        Long userNo = getCurrentUserNo();
        CartResponseDTO cartData = cartService.getCartView(userNo);
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    @PostMapping("/cart/items")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addItemToCart(
            @RequestBody CartRequestDTO.AddItem requestDTO
    ){
        Long userNo = getCurrentUserNo();
        CartResponseDTO cartData = cartService.addItem(
                userNo,
                requestDTO.getProductId(),
                requestDTO.getQuantity()
        );
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    @PatchMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody CartRequestDTO.UpdateItem requestDTO
    ){
        Long userNo = getCurrentUserNo();
        CartResponseDTO cartData = cartService.updateItem(
                userNo,
                cartItemId,
                requestDTO.getQuantity(),
                requestDTO.getSelected()
        );
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    @PatchMapping("/cart")
    public ResponseEntity<ApiResponse<CartResponseDTO>> updateAllCartItems(
            @RequestBody CartRequestDTO.UpdateAllSelected requestDTO
    ){
        Long userNo = getCurrentUserNo();
        CartResponseDTO cartData = cartService.updateAllItem(
                userNo,
                requestDTO.getSelected()
        );
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> deleteCartItem(
            @PathVariable Long cartItemId
    ){
        Long userNo = getCurrentUserNo();
        CartResponseDTO cartData = cartService.deleteItem(userNo, cartItemId);
        return ResponseEntity.ok(ApiResponse.success(cartData));
    }

    @DeleteMapping("/cart/items")
    public ResponseEntity<ApiResponse<CartResponseDTO>> deleteSelectedCartItems(
            @RequestParam(name = "selected") boolean selected
    ){
        if(selected){
            Long userNo = getCurrentUserNo();
            CartResponseDTO cartData = cartService.deleteSelectedItem(userNo);
            return ResponseEntity.ok(ApiResponse.success(cartData));
        }

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, "BAD_REQUEST"));

    }
}
