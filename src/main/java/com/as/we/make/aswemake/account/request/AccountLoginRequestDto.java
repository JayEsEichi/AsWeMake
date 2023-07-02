package com.as.we.make.aswemake.account.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountLoginRequestDto {
    private String accountEmail;
    private String accountPwd;
}
