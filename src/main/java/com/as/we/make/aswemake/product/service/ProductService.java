package com.as.we.make.aswemake.product.service;

import com.as.we.make.aswemake.product.request.ProductRequestDto;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    /** 상품 생성 **/
    public ResponseEntity<ResponseBody> createProduct(HttpServletRequest request, ProductRequestDto productRequestDto){


        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, ""), HttpStatus.OK);
    }
}
