package com.as.we.make.aswemake.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderResponseDto {
    // 주문 등록한 계정의 이메일
    private String accountEmail;
    // 주문 등록 시 배달비
    private Integer deliveryPay;
    // 주문 등록된 상품들의 정보
    private List<OrderProductResponseVo> orderProductList;
}
