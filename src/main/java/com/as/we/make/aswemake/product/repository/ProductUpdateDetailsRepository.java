package com.as.we.make.aswemake.product.repository;

import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.domain.ProductUpdateDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductUpdateDetailsRepository extends JpaRepository<ProductUpdateDetails, Long> {

    /** 조회하고자 하는 상품의 정보 이력들 조회 **/
    Optional<List<ProductUpdateDetails>> findAllByProductOrderByUpdateTimeDesc(Product product);

    /** 상품을 삭제 하게 될 때 해당되는 상품 정보 이력들도 같이 삭제 **/
    void deleteAllByProduct(Product product);
}
