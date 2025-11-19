package com.routy.routyback.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CartRequestDTO {

    @Getter
    @Setter
    public static class AddItem {

        private Long productId;
        private int quantity;
    }

    @Getter
    @Setter
    public static class UpdateItem {

        private Integer quantity;
    }

    @Getter
    @Setter
    public static class DeleteItems {

        private List<Long> cartItemIds;
    }
}
