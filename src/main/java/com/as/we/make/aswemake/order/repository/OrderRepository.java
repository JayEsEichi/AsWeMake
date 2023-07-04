package com.as.we.make.aswemake.order.repository;

import com.as.we.make.aswemake.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
