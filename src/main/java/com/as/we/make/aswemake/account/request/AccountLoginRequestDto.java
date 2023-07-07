package com.as.we.make.aswemake.account.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountLoginRequestDto {
    // 계정 로그인 요청 이메일
    private String accountEmail;
    // 계정 비밀번호
    private String accountPwd;
}
