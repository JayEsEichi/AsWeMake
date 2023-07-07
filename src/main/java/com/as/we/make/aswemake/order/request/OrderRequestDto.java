package com.as.we.make.aswemake.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderRequestDto {
    // 주문 등록 시 상품의 개수
    private Integer productCount;
    // 주문 등록하고자 하는 상품의 id
    private Long productId;
}
