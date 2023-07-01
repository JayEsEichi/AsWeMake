package com.as.we.make.aswemake.exception;

import com.as.we.make.aswemake.account.request.AccountRequestDto;
import org.springframework.stereotype.Component;

@Component
public class AccountException implements AccountExceptionInterface{

    @Override
    public boolean checkAccountCreateInfo(AccountRequestDto accountRequestDto) {

        if(accountRequestDto.getAccountEmail() == null ||
        accountRequestDto.getAccountPwd() == null ||
        accountRequestDto.getAuthority() == null){
            return true;
        }

        return false;
    }
}
