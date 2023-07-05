package com.as.we.make.aswemake.order.domain;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long ordersId;

    @Column(nullable = false)
    private Integer deliveryPay;

    @Column
    private Integer totalPrice;

    @ElementCollection
    @MapKeyColumn(name = "productId")
    @Column(name = "productCount")
    private Map<Product, Integer> products = new HashMap<>();

    @JsonIgnore
    @JoinColumn(name = "accountId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;
}
