package com.routy.routyback.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CartRequestDTO {

    @Getter
    @Setter
    public static class AddItem {

        private String userId;
        private Long productId;
        private int quantity;
    }

    @Getter
    @Setter
    public static class UpdateItem {

        private String userId;
        private Integer quantity;
    }

    @Getter
    @Setter
    public static class DeleteItems {

        private String userId;
        private List<Long> cartItemIds;
    }
}
