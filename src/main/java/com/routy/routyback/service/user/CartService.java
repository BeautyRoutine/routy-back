package com.routy.routyback.service.user;

import com.routy.routyback.dto.CartResponseDTO;
import com.routy.routyback.mapper.user.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // 비즈니스 로직 처리 - 금액 요약 계산
        long totalProductAmount = 0;
        for(CartResponseDTO.CartItemDTO item : items){
            if(item.isSelected()){
                totalProductAmount += (long) item.getPrice() * item.getQuantity();
            }
        }
        // 배송비 및 최종 결제 금액 계산
        int deliveryFee = (totalProductAmount >= 30000 || items.isEmpty()) ? 0 : 3000;
        long finalPaymentAmount = totalProductAmount + deliveryFee;

        // 요약 정보 생성
        CartResponseDTO.SummaryDTO summary = CartResponseDTO.SummaryDTO.builder()
                .totalProductAmount(totalProductAmount)
                .deliveryFee(deliveryFee)
                .finalPaymentAmount(finalPaymentAmount)
                .build();

        return CartResponseDTO.builder()
                .summary(summary)
                .items(items)
                .build();
    }

    // SkinAlertDTO 처리 (null 체크)

    // 장바구니 상품 추가 - MERGE문 사용
    @Transactional
    public CartResponseDTO addItem(Long userNo, Long productId, int quantity) {
        // MERGE 쿼리 호출
        cartMapper.insertNewItem(userNo, productId, quantity);
        return getCartView(userNo);
    }

    // 상품 속성 변경 (수량/선택여부)
    @Transactional
    public CartResponseDTO updateItem(Long userNo, long cartItemId, Integer quantity, Boolean selected){
        if(quantity != null){
            // 검증 로직
            if (quantity < 0){
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
            // 수량이 0이 되면 해당 상품 삭제
            if(quantity == 0){
                cartMapper.deleteItems(userNo, List.of(cartItemId));
            } else{
                // 수량 변경
                cartMapper.updateQuantity(userNo, cartItemId, quantity);
            }
        }

            if(selected != null){
                cartMapper.updateSelected(userNo, cartItemId, selected);
            }

            return getCartView(userNo);
    }

    // 상품 일괄 선택/해제
    @Transactional
    public CartResponseDTO updateAllItem(Long userNo, Boolean selected) {
        // selected 값이 null인 경우 예외 처리
        if(selected == null){
            throw new IllegalArgumentException("Selected 'true' or 'false' value is required.");
        }
        cartMapper.updateAllSelected(userNo, selected);

        return getCartView(userNo);
    }

    // 상품 삭제
    @Transactional
    public CartResponseDTO deleteItems(Long userNo, List<Long> cartItemIds){
        // cartItemIds가 null이거나 비어있는 경우 예외 처리
        if(cartItemIds == null || cartItemIds.isEmpty()){
            return getCartView(userNo);
        }
        cartMapper.deleteItems(userNo, cartItemIds);

        return getCartView(userNo);
    }

}
