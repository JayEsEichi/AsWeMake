package com.as.we.make.aswemake.account.service;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.response.AccountResponseDto;
import com.as.we.make.aswemake.exception.AccountExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.jwt.domain.Token;
import com.as.we.make.aswemake.jwt.dto.TokenDto;
import com.as.we.make.aswemake.jwt.repository.TokenRepository;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final AccountExceptionInterface accountExceptionInterface;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /** 계정 생성 **/
    public ResponseEntity<ResponseBody> createAccount(AccountCreateRequestDto accountCreateRequestDto) {

        // 계정 생성 정보 요청 확인
        if (accountExceptionInterface.checkAccountCreateInfo(accountCreateRequestDto)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_CREATE_ACCOUNT, null), HttpStatus.BAD_REQUEST);
        }

        // MART 계정 권한을 설정하지 않으면 초기 USER 권한
        String authority = "USER";

        // MART 계정 권한일 시 MART 권한 설정
        if(accountCreateRequestDto.getAuthority().equals("MART")){
            authority = "MART";
        }

        // account entity 계정 생성 정보 input
        Account account = Account.builder()
                .accountEmail(accountCreateRequestDto.getAccountEmail())
                .accountPwd(passwordEncoder.encode(accountCreateRequestDto.getAccountPwd()))
                .authority(Collections.singletonList(authority))
                .build();

        // 계정 저장
        accountRepository.save(account);

        // 반환 객체
        AccountResponseDto responseData = AccountResponseDto.builder()
                .accountEmail(account.getAccountEmail())
                .authority(account.getAuthority().get(0))
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("정상적으로 계정이 생성되었습니다.", responseData)), HttpStatus.OK);
    }


    /** 계정 로그인 **/
    @Transactional
    public ResponseEntity<ResponseBody> loginAccount(AccountLoginRequestDto accountLoginRequestDto, HttpServletResponse response){

        // 로그인하고자 하는 계정의 존재 여부 확인
        if(accountExceptionInterface.checkAccountLoginInfo(accountLoginRequestDto, accountRepository.findByAccountEmail(accountLoginRequestDto.getAccountEmail()).get().getAccountPwd())){
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_EXIST_EMAIL_ACCOUNT, null), HttpStatus.BAD_REQUEST);
        }

        // 정상적으로 로그인이 된 계정 정보 조회
        Account account = accountRepository.findByAccountEmail(accountLoginRequestDto.getAccountEmail()).orElseThrow(() -> new NullPointerException("존재하지 않는 계정입니다."));

        // 재로그인 시 기존에 존재하는 토큰 삭제
        if(tokenRepository.findByAccountId(account.getAccountId()) != null){
            tokenRepository.deleteByAccountId(account.getAccountId());
        }

        // 로그인 이메일과 비밀번호로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountLoginRequestDto.getAccountEmail(), accountLoginRequestDto.getAccountPwd());
        // 인증 객체에 인증 토큰을 넣어 인증 권한 발급
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 인증 객체를 통해 발급된 토큰을 Dto 객체에 저장
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        // Response Header에 액세스 토큰, 리프레시 토큰, 토큰 만료일 input (토큰 발급)
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());

        // 발급된 토큰 정보를 토대로 Token 엔티티에 input
        Token token = Token.builder()
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .accountId(accountRepository.findByAccountEmail(accountLoginRequestDto.getAccountEmail()).get().getAccountId())
                .build();

        // 토큰 저장
        tokenRepository.save(token);

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("로그인 되셨습니다.", token)), HttpStatus.OK);
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
