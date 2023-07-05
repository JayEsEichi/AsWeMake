package com.as.we.make.aswemake.pay.domain;

import com.as.we.make.aswemake.coupon.domain.Coupon;
import com.as.we.make.aswemake.order.domain.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class PaymentDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long paymentDetailsId;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Integer paymentCost;

    @Column(nullable = false)
    private Integer remainCost;

    @Column(nullable = false)
    private String couponWhether;

    @Column(nullable = false)
    private Integer discountPrice;

    @JoinColumn(name = "couponId")
    @OneToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @JoinColumn(name = "ordersId")
    @OneToOne(fetch = FetchType.LAZY)
    private Orders order;
}
