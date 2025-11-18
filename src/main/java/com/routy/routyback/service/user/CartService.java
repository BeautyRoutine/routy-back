package com.routy.routyback.service.user;

import com.routy.routyback.dto.CartResponseDTO;
import com.routy.routyback.dto.CartResponseDTO.SummaryDTO;
import com.routy.routyback.mapper.user.CartMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    // Mapper 주입
    private final CartMapper cartMapper;

    // 장바구니 조회 (목록과 금액요약 포함)
    @Transactional(readOnly = true)
    public CartResponseDTO getCartView(Long userNo) {

        // DB에서 데이터 조회
        List<CartResponseDTO.CartItemDTO> items = cartMapper.findItemsByUserNo(userNo);

        SummaryDTO summary = SummaryDTO.builder()
            .deliveryFee(3000)
            .build();

        return CartResponseDTO.builder()
            .summary(summary)
            .items(items)
            .build();
    }

    // 장바구니 상품 추가 - MERGE문 사용
    @Transactional
    public void addItem(Long userNo, Long productId, int quantity) {
        cartMapper.insertNewItem(userNo, productId, quantity);
    }

    // 상품 속성 변경 (수량/선택여부)
    @Transactional
    public void updateItem(Long userNo, long cartItemId, Integer quantity) {
        if (quantity != null) {
            // 검증 로직
            if (quantity < 0) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
            // 수량이 0이 되면 해당 상품 삭제
            if (quantity == 0) {
                cartMapper.deleteItems(userNo, List.of(cartItemId));
            } else {
                // 수량 변경
                cartMapper.updateQuantity(userNo, cartItemId, quantity);
            }
        }
    }

    // 상품 삭제
    @Transactional
    public void deleteItems(Long userNo, List<Long> cartItemIds) {
        // cartItemIds가 null이거나 비어있는 경우 예외 처리
        if (cartItemIds == null || cartItemIds.isEmpty()) {
            return;
        }
        cartMapper.deleteItems(userNo, cartItemIds);
    }

}
