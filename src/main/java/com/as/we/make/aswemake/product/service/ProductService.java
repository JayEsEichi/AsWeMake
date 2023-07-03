package com.as.we.make.aswemake.product.service;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.exception.account.AccountExceptionInterface;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.product.request.ProductCreateRequestDto;
import com.as.we.make.aswemake.product.request.ProductUpdateRequestDto;
import com.as.we.make.aswemake.product.response.ProductResponseDto;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final TokenExceptionInterface tokenExceptionInterface;
    private final AccountExceptionInterface accountExceptionInterface;
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;


    /** 상품 생성 **/
    public ResponseEntity<ResponseBody> createProduct(HttpServletRequest request, ProductCreateRequestDto productCreateRequestDto){

        // 요청 토큰 확인
        if(!tokenExceptionInterface.checkToken(request)){
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // MART 권한을 가진 계정만 상품 관리 가능
        if(!accountExceptionInterface.checkAuthority(account.getAuthority().get(0))){
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_POSSIBLE_AUTHORITY, null), HttpStatus.BAD_REQUEST);
        }

        // 생성할 상품 정보 input
        Product product = Product.builder()
                .productName(productCreateRequestDto.getProductName())
                .price(productCreateRequestDto.getPrice())
                .account(account)
                .build();

        // 상품 생성
        productRepository.save(product);

        // 생성 확인을 위한 ResponseDto 객체에 정보 저장
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품 생성이 완료되었습니다.", productResponseDto)), HttpStatus.OK);
    }


    // 상품 가격 수정
    @Transactional
    public ResponseEntity<ResponseBody> updateProduct(HttpServletRequest request, ProductUpdateRequestDto productUpdateRequestDto){

        // 요청 토큰 확인
        if(!tokenExceptionInterface.checkToken(request)){
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // MART 권한을 가진 계정만 상품 관리 가능
        if(!accountExceptionInterface.checkAuthority(account.getAuthority().get(0))){
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_POSSIBLE_AUTHORITY, null), HttpStatus.BAD_REQUEST);
        }

        Product updateProduct = productRepository.findByAccountAndProductId(account, productUpdateRequestDto.getProductId())
                .orElseThrow(() -> new NullPointerException("수정 요청한 계정의 상품이 아니라서 수정할 수 없습니다."));

        // 가격 수정
        updateProduct.setPrice(productUpdateRequestDto.getPrice());

        // 수정 확인을 위한 ResponseDto 객체에 정보 저장
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productName(updateProduct.getProductName())
                .price(updateProduct.getPrice())
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("정상적으로 수정되었습니다.", productResponseDto)), HttpStatus.OK);
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
