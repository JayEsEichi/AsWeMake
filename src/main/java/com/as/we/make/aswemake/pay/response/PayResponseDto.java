package com.as.we.make.aswemake.pay.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PayResponseDto {
    // 총 금액
    private Integer totalPrice;
    // 지불 금액
    private Integer paymentCost;
    // 잔액
    private Integer remainCost;
    // 쿠폰 사용 여부
    private String couponWhether;
    // 할인된 금액
    private Integer discountPrice;
}
