package com.as.we.make.aswemake.exception.token;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenExceptionInterface {

    /** 토큰 정합성 검증 **/
    boolean checkToken(HttpServletRequest request);
}
