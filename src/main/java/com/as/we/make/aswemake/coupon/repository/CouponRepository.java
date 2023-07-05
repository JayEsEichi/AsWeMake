package com.as.we.make.aswemake.coupon.repository;

import com.as.we.make.aswemake.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /** 사용할 쿠폰 조회 **/
    Coupon findByCouponId(Long couponId);
}
