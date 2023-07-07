package com.as.we.make.aswemake.share;

import lombok.Getter;

@Getter
public class ResponseBody <T> {

    // 결과를 반환할 상태 메세지
    private final String statusMessage;
    // 결과를 반환할 상태 코드
    private final Integer statusCode;
    // 반환할 데이터
    private final T data;

    public ResponseBody(StatusCode statusCode, T data){
        this.statusMessage = statusCode.statusMessage;
        this.statusCode = statusCode.statusCode;
        this.data = data;
    }
}
