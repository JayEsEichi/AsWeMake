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
    private Long productId;
    private String productName;
    private Integer price;
    private Integer productCount;
}
