package com.routy.routyback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class CartResponseDTO {

    private SummaryDTO summary;
    private List<CartItemDTO> items;

    @Getter
    @Builder
    public static class SummaryDTO {
        private long totalProductAmount;
        private int deliveryFee;
        private long finalPaymentAmount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemDTO {
        private Long cartItemId;
        private Long productId;
        private String name;
        private String brand;
        private int price;
        private int quantity;
        private String imageUrl;
        private boolean selected;
        private SkinAlertDTO skinAlert;
    }

    @Getter
    @Builder
    public static class SkinAlertDTO {
        private String type;
        private String message;
    }

}
