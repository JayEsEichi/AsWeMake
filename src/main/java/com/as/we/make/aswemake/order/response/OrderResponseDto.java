package com.as.we.make.aswemake.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderResponseDto {
    private String accountEmail;
    private Integer deliveryPay;
    private List<OrderProductResponseVo> orderProductList;
}
