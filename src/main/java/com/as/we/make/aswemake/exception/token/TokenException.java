package com.as.we.make.aswemake.exception.token;

import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenException implements TokenExceptionInterface{

    private final JwtTokenProvider jwtTokenProvider;

    /** 토큰 정합성 검증 **/
    @Override
    public boolean checkToken(HttpServletRequest request) {

        if(jwtTokenProvider.validateToken(request.getHeader("Refresh-Token"))){
            return true;
        }
        return false;
    }
}
