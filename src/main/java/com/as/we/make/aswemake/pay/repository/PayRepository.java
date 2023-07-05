package com.as.we.make.aswemake.pay.repository;

import com.as.we.make.aswemake.pay.domain.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<PaymentDetails, Long> {
}
