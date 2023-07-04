package com.as.we.make.aswemake.order.repository;

import com.as.we.make.aswemake.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    /** 주문 조회 **/
    Optional<Orders> findByOrdersId(Long ordersId);
}
