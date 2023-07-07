package com.as.we.make.aswemake.exception.account;

import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;

public interface AccountExceptionInterface {

    /** 계정 생성 정보들 중에 null 값 유무 확인 **/
    boolean checkAccountCreateInfo(AccountCreateRequestDto accountCreateRequestDto);

    /** 가입된 계정의 패스워드와 로그인하고자 하는 계정의 정보의 일치 여부 확인 **/
    boolean checkAccountLoginInfo(AccountLoginRequestDto accountLoginRequestDto, String existPwd);

    /** 상품 관리는 MART 권한을 가진 계정만 가능함을 확인 **/
    boolean checkAuthority(String authority);

    /** 이메일 중복 가입 확인 **/
    boolean checkDuplicateEmail(String accountEmail);
}
