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
    private Integer productCount;
    private Long productId;
}
