package com.as.we.make.aswemake.account.service;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountRequestDto;
import com.as.we.make.aswemake.account.response.AccountResponseDto;
import com.as.we.make.aswemake.exception.AccountExceptionInterface;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountExceptionInterface accountExceptionInterface;

    // 계정 생성
    public ResponseEntity<ResponseBody> createAccount(AccountRequestDto accountRequestDto) {

        // 계정 생성 정보 요청 확인
        if (accountExceptionInterface.checkAccountCreateInfo(accountRequestDto)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_CREATE_ACCOUNT, null), HttpStatus.BAD_REQUEST);
        }

        // account entity 계정 생성 정보 input
        Account account = Account.builder()
                .accountEmail(accountRequestDto.getAccountEmail())
                .accountPwd(accountRequestDto.getAccountPwd())
                .authority(accountRequestDto.getAuthority())
                .build();

        // 계정 저장
        accountRepository.save(account);

        // 반환 객체
        AccountResponseDto responseData = AccountResponseDto.builder()
                .accountEmail(account.getAccountEmail())
                .authority(account.getAuthority())
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("정상적으로 계정이 생성되었습니다.", responseData)), HttpStatus.OK);
    }


    // 최종적으로 결과를 반환하기 위한 method
    private LinkedHashMap<String, Object> resultSet(String message, Object responseData) {

        // 정상 반환 시 반환되는 메세지와 데이터
        LinkedHashMap<String, Object> resultSet = new LinkedHashMap<>();
        resultSet.put("resultMessage", message);
        resultSet.put("resultData", responseData);

        return resultSet;
    }

}
