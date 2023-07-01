package com.as.we.make.aswemake.account.controller;

import com.as.we.make.aswemake.account.request.AccountRequestDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.share.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/awm")
@Slf4j
@RestController
public class AccountController {

    private final AccountService accountService;

    // 계정 생성
    @PostMapping("/account/create")
    public ResponseEntity<ResponseBody> createAccount(@RequestBody AccountRequestDto accountRequestDto){
        log.info("계정 생성 api - controller : 아이디 = {}, 비밀번호 = {}, 권한 = {}", accountRequestDto.getAccountEmail(), accountRequestDto.getAccountPwd(), accountRequestDto.getAuthority());

        return accountService.createAccount(accountRequestDto);
    }

}
