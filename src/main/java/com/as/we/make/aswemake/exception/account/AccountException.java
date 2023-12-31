package com.as.we.make.aswemake.exception.account;

import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountException implements AccountExceptionInterface {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 계정 생성 정보 확인
     **/
    @Override
    public boolean checkAccountCreateInfo(AccountCreateRequestDto accountCreateRequestDto) {

        if (accountCreateRequestDto.getAccountEmail() == null ||
                accountCreateRequestDto.getAccountPwd() == null ||
                accountCreateRequestDto.getAuthority() == null) {
            return true;
        }

        return false;
    }


    /**
     * 로그인 계정 정보 일치 여부 확인
     **/
    @Override
    public boolean checkAccountLoginInfo(AccountLoginRequestDto accountLoginRequestDto, String existPwd) {

        if (!accountRepository.findByAccountEmail(accountLoginRequestDto.getAccountEmail()).isPresent() ||
                !passwordEncoder.matches(accountLoginRequestDto.getAccountPwd(), existPwd)) {
            return true;
        }

        return false;
    }

    /** 상품 관리 계정 권한 확인 **/
    @Override
    public boolean checkAuthority(String authority) {

        if(authority.equals("MART")){
            return true;
        }

        return false;
    }

    /** 이메일 중복 확인 **/
    @Override
    public boolean checkDuplicateEmail(String accountEmail) {

        if(accountRepository.findByAccountEmail(accountEmail).isPresent()){
            return true;
        }

        return false;
    }

}
