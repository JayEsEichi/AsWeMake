package com.as.we.make.aswemake.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductUpdateRequestDto {
    private Long productId;
    private Integer price;
}
