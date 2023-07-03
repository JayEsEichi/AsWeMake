package com.as.we.make.aswemake.account;

import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.exception.account.AccountExceptionInterface;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


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
        AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder()
                .accountEmail("mart@naver.com")
                .accountPwd("mart9999!!!!")
                .authority("mart")
                .build();

        // 계정 생성 후 반환되는 상태 코드 값
        int statusCode = accountService.createAccount(accountCreateRequestDto).getBody().getStatusCode();

        // 생성이 완료된 이후 반환 데이터 String으로 변환하여 확인
        String resultData = new Gson().toJson(accountService.createAccount(accountCreateRequestDto).getBody().getData());
        System.out.println("반환되는 데이터 : " + resultData);

        // then
        // 상태 코드 값이 정상처리된 200인지 확인
        assertThat(statusCode).isEqualTo(200);
    }

    @DisplayName("로그인 Service 테스트")
    @Test
    void loginAccountTest(){

        // 테스트 계정 생성 객체
        AccountLoginRequestDto accountLoginRequestDto = AccountLoginRequestDto.builder()
                .accountEmail("mart@naver.com")
                .accountPwd("mart9999!!!!")
                .build();

        // 계정 생성 후 반환되는 상태 코드 값
        int statusCode = accountService.loginAccount(accountLoginRequestDto, any(MockHttpServletResponse.class)).getBody().getStatusCode();

        // 생성이 완료된 이후 반환 데이터 String으로 변환하여 확인
        String resultData = new Gson().toJson(accountService.loginAccount(accountLoginRequestDto, any(HttpServletResponse.class)).getBody().getData());
        System.out.println("반환되는 데이터 : " + resultData);

        // then
        // 상태 코드 값이 정상처리된 200인지 확인
        assertThat(statusCode).isEqualTo(200);
    }
}
