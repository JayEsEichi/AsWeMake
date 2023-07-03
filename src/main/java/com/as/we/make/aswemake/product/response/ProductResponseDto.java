package com.as.we.make.aswemake.product.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductResponseDto {
    private String productName;
    private Integer price;
}
