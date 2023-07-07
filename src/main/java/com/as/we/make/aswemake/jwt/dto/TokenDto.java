package com.as.we.make.aswemake.jwt.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class TokenDto {
    // 토큰 구분 값
    private String grantType;
    // 액세스 토큰
    private String accessToken;
    // 리프레시 토큰
    private String refreshToken;
    // 토큰 만료 기간
    private Date accessTokenExpiresIn;
}
