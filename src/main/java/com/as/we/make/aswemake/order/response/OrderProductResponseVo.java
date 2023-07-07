package com.as.we.make.aswemake.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderProductResponseVo {
    // 상품 id
    private Long productId;
    // 상품 이름
    private String productName;
    // 상품 가격
    private Integer price;
    // 상품 개수
    private Integer productCount;
}
