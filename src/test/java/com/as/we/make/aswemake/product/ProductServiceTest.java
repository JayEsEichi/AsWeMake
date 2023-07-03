package com.as.we.make.aswemake.product;

import com.as.we.make.aswemake.account.controller.AccountController;
import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.exception.account.AccountExceptionInterface;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtAuthenticationFilter;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.jwt.dto.TokenDto;
import com.as.we.make.aswemake.jwt.repository.TokenRepository;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.product.request.ProductRequestDto;
import com.as.we.make.aswemake.product.service.ProductService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountController accountController;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountExceptionInterface accountExceptionInterface;

    @Mock
    private TokenExceptionInterface tokenExceptionInterface;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtTokenProvider jwtTokenProvider;


    @DisplayName("계정 생성 Service 테스트")
    @Test
    void createProductTest() throws Exception {

        // 테스트 계정 생성 객체
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .productName("마우스패드")
                .price(10000)
                .build();

//        DispatcherServlet servlet = new DispatcherServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJ0QG5hdmVyLmNvbSIsImF1dGgiOiJNQVJUIiwiZXhwIjoxNjg4NDM0NTQ3fQ.HFHrEqOjI2DZXIqGo1djiLPsJFu4iMnhbBX1kFL15Kg");
//        request.addHeader("Refresh-Token", "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg0MzQ1NDd9.NN_MxGexNeRGioz14X3t79pdEnQPPJKRSuXxcoCkwd0");
        MockHttpServletResponse response = new MockHttpServletResponse();

//        servlet.service(request, response);

//        accountService.createAccount(immediateAccountCreate());
//        System.out.println(accountRepository.findByAccountEmail(immediateAccountCreate().getAccountEmail()));

//        accountService.loginAccount(immediateAccountLogin(), response);

        // account entity 계정 생성 정보 input
        Account account = Account.builder()
                .accountEmail("test@naver.com")
                .accountPwd("test0000!!!!")
                .authority(Collections.singletonList("MART"))
                .accountId(99L)
                .build();

        // 계정 저장
        accountRepository.save(account);
//        SecurityMockMvcConfigurers.springSecurity(authenticationFilter);
        // 로그인 이메일과 비밀번호로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(immediateAccountLogin().getAccountEmail(), immediateAccountLogin().getAccountPwd());
        System.out.println(authenticationManagerBuilder.getObject());

        // 인증 객체에 인증 토큰을 넣어 인증 권한 발급
        Authentication authentication = authenticationManagerBuilder.build().authenticate(authenticationToken);
//        authentication
        // 인증 객체를 통해 발급된 토큰을 Dto 객체에 저장
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication, account.getAuthority().get(0));

        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());


        // 계정 생성 후 반환되는 상태 코드 값
//        int statusCode = productService.createProduct(request, productRequestDto).getBody().getStatusCode();

        // then
        // 상태 코드 값이 정상처리된 200인지 확인
//        assertThat(statusCode).isEqualTo(200);

        System.out.println(request.getHeader("Authorization"));
        System.out.println(response.getHeader("Authorization"));
    }


    private AccountCreateRequestDto immediateAccountCreate() {

        AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder()
                .accountEmail("test@naver.com")
                .accountPwd("test0000!!!!")
                .authority("MART")
                .build();

        return accountCreateRequestDto;
    }

    private AccountLoginRequestDto immediateAccountLogin() {
        AccountLoginRequestDto accountLoginRequestDto = AccountLoginRequestDto.builder()
                .accountEmail("test@naver.com")
                .accountPwd("test0000!!!!")
                .build();

        return accountLoginRequestDto;
    }

}
