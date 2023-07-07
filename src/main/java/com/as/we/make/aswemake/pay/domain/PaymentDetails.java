package com.as.we.make.aswemake.pay.domain;

import com.as.we.make.aswemake.coupon.domain.Coupon;
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

    // 결제 내역 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long paymentDetailsId;

    // 결제 총 금액
    @Column(nullable = false)
    private Integer totalPrice;

    // 지불 금액
    @Column(nullable = false)
    private Integer paymentCost;

    // 잔액
    @Column(nullable = false)
    private Integer remainCost;

    // 쿠폰 사용 여부 (O / X)
    @Column(nullable = false)
    private String couponWhether;

    // 할인된 금액
    @Column(nullable = false)
    private Integer discountPrice;

    // 결제할 주문의 id
    @Column(nullable = false)
    private Long ordersId;

    // 쿠폰 사용 시 사용되는 쿠폰
    @JoinColumn(name = "couponId")
    @OneToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

}
