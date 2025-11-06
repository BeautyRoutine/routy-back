package com.routy.routyback.service.user;

import com.routy.routyback.dto.CartViewResponseDTO;
import com.routy.routyback.mapper.user.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    public CartViewResponseDTO getCartView(Long userNo) {

        List<CartViewResponseDTO.CartItemDTO> items = cartMapper.findItemByUserNo(userNo);

        long totalProductAmount = 0;
        for(CartViewResponseDTO.CartItemDTO item : items){
            if(item.isSelected()){
                totalProductAmount += (long) item.getPrice() * item.getQuantity();
            }
        }

        int deliveryFee = (totalProductAmount >= 30000 || items.isEmpty()) ? 0 : 3000;
        long finalPaymentAmount = totalProductAmount + deliveryFee;

        CartViewResponseDTO.SummaryDTO summary = CartViewResponseDTO.SummaryDTO.builder()
                .totalProductAmount(totalProductAmount)
                .deliveryFee(deliveryFee)
                .finalPaymentAmount(finalPaymentAmount)
                .build();

        return CartViewResponseDTO.builder()
                .summary(summary)
                .items(items)
                .build();
    }

    @Transactional
    public CartViewResponseDTO addItem(Long userNo, Long productId, int quantity) {
        Long existingCartItemId = cartMapper.findCartItemIdByUserAndProduct(userNo, productId);

        if(existingCartItemId != null){
            cartMapper.addStock(existingCartItemId, quantity);
        } else{
            cartMapper.insertItem(userNo, productId, quantity);
        }

        return getCartView(userNo);
    }

    @Transactional
    public CartViewResponseDTO updateItem(Long userNo, long cartItemId, Integer quantity, Boolean selected){
        if(quantity != null){
            if(quantity <= 0){
                cartMapper.deleteItem(userNo, cartItemId);
            } else{
                cartMapper.updateQuantity(userNo, cartItemId, quantity);
            }
        }

            if(selected != null){
                cartMapper.updateSelected(userNo, cartItemId, selected);
            }

            return getCartView(userNo);
    }

    @Transactional
    public CartViewResponseDTO updateAllItem(Long userNo, boolean selected) {

        cartMapper.updateAllSelected(userNo, selected);

        return getCartView(userNo);
    }

    @Transactional
    public CartViewResponseDTO deleteItem(Long userNo, Long cartItemId){

        cartMapper.deleteItem(userNo, cartItemId);

        return getCartView(userNo);
    }

    @Transactional
    public CartViewResponseDTO deleteSelectedItem(Long userNo){

        cartMapper.deleteSelectedItems(userNo);

        return getCartView(userNo);
    }
}
