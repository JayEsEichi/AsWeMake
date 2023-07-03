package com.as.we.make.aswemake.share;

import lombok.Getter;

@Getter
public class ResponseBody <T> {

    private final String statusMessage;
    private final Integer statusCode;
    private final T data;

    public ResponseBody(StatusCode statusCode, T data){
        this.statusMessage = statusCode.statusMessage;
        this.statusCode = statusCode.statusCode;
        this.data = data;
    }
}
