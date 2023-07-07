package com.as.we.make.aswemake.coupon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Coupon {

    // 쿠폰 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long couponId;

    // 쿠폰 타입 (FIX / RATIO)
    @Column(nullable = false)
    private String couponType;

    // 쿠폰 타입 별 할인 금액 내용
    @Column(nullable = false)
    private float discountContent;

}
