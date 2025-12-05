package com.routy.routyback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInsertRequest {
	private ProductDTO product;
	private ProductDetailDTO prdDetail;

}
