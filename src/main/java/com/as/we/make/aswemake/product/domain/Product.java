package com.as.we.make.aswemake.product.domain;

import com.as.we.make.aswemake.account.domain.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @JsonIgnore
    @JoinColumn(name = "accountId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

}
