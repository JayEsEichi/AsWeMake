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
    // 수정할 상품의 id
    private Long productId;
    // 수정할 상품의 가격
    private Integer price;
}
