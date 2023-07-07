package com.as.we.make.aswemake.account.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountResponseDto {
    // 계정 결과 반환 이메일
    private String accountEmail;
    // 계정 결과 반환 권한
    private String authority;
}
