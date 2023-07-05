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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long couponId;

    @Column(nullable = false)
    private String couponType;

    @Column(nullable = false)
    private float discountContent;

}
