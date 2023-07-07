package com.as.we.make.aswemake.product.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductResponseDto {
    // 상품 이름
    private String productName;
    // 상품 가격
    private Integer price;
}
