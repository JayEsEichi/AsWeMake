package com.as.we.make.aswemake.product.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductRequestDto {
    private String productName;
    private Integer price;
}
