package com.as.we.make.aswemake.product.domain;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.share.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Product extends Timestamped {

    // 상품 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long productId;

    // 상품 이름
    @Column(nullable = false)
    private String productName;

    // 상품 가격
    @Column(nullable = false)
    private Integer price;

    // 상품을 생성한 계정
    @JsonIgnore
    @JoinColumn(name = "accountId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

}
