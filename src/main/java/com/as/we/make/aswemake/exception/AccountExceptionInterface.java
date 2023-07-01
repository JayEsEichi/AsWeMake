package com.as.we.make.aswemake.exception;

import com.as.we.make.aswemake.account.request.AccountRequestDto;

public interface AccountExceptionInterface {

    /** 계정 생성 정보들 중에 null 값 유무 확인 **/
    boolean checkAccountCreateInfo(AccountRequestDto accountRequestDto);
}
