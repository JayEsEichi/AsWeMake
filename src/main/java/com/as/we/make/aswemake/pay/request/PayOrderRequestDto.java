package com.as.we.make.aswemake.pay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PayOrderRequestDto {
    // 결제할 주문 내역 id
    private Long ordersId;
    // 지불 금액
    private Integer paymentCost;
    // 쿠폰 사용 여부 (O / X)
    private String couponWhether;
    // 사용할 쿠폰 id
    private Long couponId;
    // 쿠폰 적용 범위 (ALL / SPECIFIC)
    private String couponScope;
    // scope 가 specific 일 경우에만 적용 상품 id가 들어옵니다.
    private Long specificProductId;

    /**
     * 1. couponWhether가 O일 경우, couponId와 couponScope 는 반드시 같이 입력되어야 합니다.
     * 2. couponScope가 SPECIFIC일 경우에만, specificProductId 에 id 값이 입력되어있어야 합니다.
     *
     */
}
