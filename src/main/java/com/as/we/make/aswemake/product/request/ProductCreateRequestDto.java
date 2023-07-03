package com.as.we.make.aswemake.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductCreateRequestDto {
    private String productName;
    private Integer price;
}
