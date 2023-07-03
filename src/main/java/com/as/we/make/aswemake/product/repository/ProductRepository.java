package com.as.we.make.aswemake.product.repository;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /** 관리하고자 하는 상품 조회 **/
    Optional<Product> findByAccountAndProductId(Account account, Long productId);

    /** 상품 삭제 **/
    void deleteByAccountAndProductId(Account account, Long productId);
}
