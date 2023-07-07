package com.as.we.make.aswemake.jwt.domain;

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
public class Token {

    // JWT 토큰 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long tokenId;

    // 토큰 구분 값 (Bearer)
    @Column(nullable = false)
    private String grantType;

    // JWT 액세스 토큰
    @Column(nullable = false)
    private String accessToken;

    // JWT 리프레시 토큰
    @Column(nullable = false)
    private String refreshToken;

    // 토큰 발급된 계정의 id
    @Column
    private Long accountId;
}
