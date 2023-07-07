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

    // 주문 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long ordersId;

    // 배달비
    @Column(nullable = false)
    private Integer deliveryPay;

    // 총 금액
    @Column
    private Integer totalPrice;

    // 주문에 포함되는 상품들과 개수 저장 (OrdersProducts 엔티티가 추가로 생성되어 관리)
    @ElementCollection
    @MapKeyColumn(name = "productId")
    @Column(name = "productCount")
    private Map<Product, Integer> products = new HashMap<>();

    // 주문을 등록한 계정
    @JsonIgnore
    @JoinColumn(name = "accountId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;
}
