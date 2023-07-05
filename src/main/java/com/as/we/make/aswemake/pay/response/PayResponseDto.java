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
    private Integer totalPrice;
    private Integer paymentCost;
    private Integer remainCost;
    private String couponWhether;
    private Integer discountPrice;
}
