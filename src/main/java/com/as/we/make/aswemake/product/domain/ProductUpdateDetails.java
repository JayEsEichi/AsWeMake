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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long productUpdateDetailsId;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private LocalDateTime updateTime;

    @JsonIgnore
    @JoinColumn(name = "productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
