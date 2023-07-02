package com.as.we.make.aswemake.account.controller;

import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.share.ResponseBody;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/awm/account")
@Slf4j
@RestController
public class AccountController {

    private final AccountService accountService;

    // 계정 생성
    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createAccount(@RequestBody AccountCreateRequestDto accountCreateRequestDto){
        log.info("계정 생성 api - controller : 아이디 = {}, 비밀번호 = {}, 권한 = {}", accountCreateRequestDto.getAccountEmail(), accountCreateRequestDto.getAccountPwd(), accountCreateRequestDto.getAuthority());

        return accountService.createAccount(accountCreateRequestDto);
    }

    // 계정 로그인
    @GetMapping("/login")
    public ResponseEntity<ResponseBody> loginAccount(@RequestBody AccountLoginRequestDto accountLoginRequestDto, HttpServletResponse response){
        log.info("계정 로그인 api - controller : 아이디 = {}", accountLoginRequestDto.getAccountEmail());

        return accountService.loginAccount(accountLoginRequestDto, response);
    }

}
