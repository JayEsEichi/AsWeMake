package com.as.we.make.aswemake.share;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
public enum StatusCode {

    // 정상 응답 처리
    IT_WORK(200, "정상 처리"),

    // 계정 생성 정보 요청 에러
    CANNOT_CREATE_ACCOUNT(452, "계정 생성 요청 정보가 옳지 않아 생성할 수 없습니다.");

    public Integer statusCode;
    public String statusMessage;

}
