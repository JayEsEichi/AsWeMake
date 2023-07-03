package com.as.we.make.aswemake.product.repository;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /** 수정하고자 하는 상품 조회 **/
    Optional<Product> findByAccountAndProductId(Account account, Long productId);
}
