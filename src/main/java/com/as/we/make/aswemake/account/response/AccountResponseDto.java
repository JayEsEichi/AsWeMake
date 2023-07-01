package com.as.we.make.aswemake.account.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountResponseDto {
    private String accountEmail;
    private String authority;
}
