package com.as.we.make.aswemake.product.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class ProductUpdateDetails {

    // 상품 이력 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long productUpdateDetailsId;

    // 상품 가격
    @Column(nullable = false)
    private Integer price;

    // 상품 이름
    @Column(nullable = false)
    private String productName;

    // 상품 가격의 변동된 시간
    @Column(nullable = false)
    private LocalDateTime updateTime;

    // 상품 이력이 생성되는 상품
    @JsonIgnore
    @JoinColumn(name = "productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
