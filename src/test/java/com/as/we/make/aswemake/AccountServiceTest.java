package com.as.we.make.aswemake;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountRequestDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.exception.AccountExceptionInterface;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountExceptionInterface accountExceptionInterface;

    @DisplayName("계정 생성 Service 테스트")
    @Test
    void createAccountTest(){

        // 테스트 계정 생성 객체
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .accountEmail("mart@naver.com")
                .accountPwd("mart9999!!!!")
                .authority("mart")
                .build();

        // 계정 생성 후 반환되는 상태 코드 값
        int statusCode = accountService.createAccount(accountRequestDto).getBody().getStatusCode();

        // 생성이 완료된 이후 반환 데이터 String으로 변환하여 확인
        String resultData = new Gson().toJson(accountService.createAccount(accountRequestDto).getBody().getData());
        System.out.println("반환되는 데이터 : " + resultData);

        // then
        // 상태 코드 값이 정상처리된 200인지 확인
        assertThat(statusCode).isEqualTo(200);
    }
}
