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
    CANNOT_POSSIBLE_AUTHORITY(455, "상품 관리가 가능한 권한이 아닙니다."),
    // 결제하고자 하는 주문 내역이 자기가 등록한 주문 내역이 아닐 경우
    CANNOT_PAY_ORDER(456, "본인의 주문 내역이 아니라서 결제할 수 없습니다."),
    // 주문 내역보다 결제 금액이 부족할 경우
    SHORTAGE_OF_MONEY(457, "결제 금액이 부족합니다."),
    // 이메일이 중복일 경우
    DUPLICATE_ACCOUNT_EMAIL(458, "이미 존재하는 이메일 계정입니다.");

    public Integer statusCode;
    public String statusMessage;

}
