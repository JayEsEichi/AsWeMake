package com.as.we.make.aswemake.account.domain;

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
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long accountId;

    @Column(nullable = false)
    private String accountEmail;

    @Column(nullable = false)
    private String accountPwd;

    @Column(nullable = false)
    private String authority;
}
