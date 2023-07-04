package com.as.we.make.aswemake.order.domain;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long ordersId;

    @Column(nullable = false)
    private Integer deliveryPay;

    @ElementCollection
    @MapKeyColumn(name = "productId")
    @Column(name = "productCount")
    private Map<Product, Integer> products = new HashMap<>();

    @JsonIgnore
    @JoinColumn(name = "orderAccountId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
