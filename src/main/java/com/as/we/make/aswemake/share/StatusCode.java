package com.as.we.make.aswemake.share;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum StatusCode {

    // 정상 응답 처리
    IT_WORK(200, "정상 처리"),

    // 계정 생성 정보 요청 에러
    CANNOT_CREATE_ACCOUNT(452, "계정 생성 요청 정보가 옳지 않아 생성할 수 없습니다."),
    // 존재하지 않는 이메일 계정
    NOT_EXIST_EMAIL_ACCOUNT(453, "존재하지 않는 이메일 계정입니다."),
    // 정상적으로 인증된 토큰이 아닌 경우
    NOT_CORRECT_TOKEN(454, "인증된 토큰이 아닙니다."),
    // 상품 관리 가능 권한이 아닐 경우
    CANNOT_POSSIBLE_AUTHORITY(455, "상품 관리가 가능한 권한이 아닙니다.");

    public Integer statusCode;
    public String statusMessage;

}
