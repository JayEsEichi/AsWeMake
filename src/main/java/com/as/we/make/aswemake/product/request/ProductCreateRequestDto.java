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
    // 생성할 상품의 이름
    private String productName;
    // 생성할 상품의 가격
    private Integer price;
}
