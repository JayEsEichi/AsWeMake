package com.as.we.make.aswemake.product.controller;

import com.as.we.make.aswemake.product.request.ProductRequestDto;
import com.as.we.make.aswemake.product.service.ProductService;
import com.as.we.make.aswemake.share.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/awm/product")
@RestController
public class ProductController {

    private final ProductService productService;

    // 상품 생성
    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createProduct(HttpServletRequest request, @RequestBody ProductRequestDto productRequestDto){
        log.info("상품 생성 api - controller : 상품명 = {], 상품 가격 = {}, 생성자 = {}", productRequestDto.getProductName(), productRequestDto.getPrice(), request);

        return productService.createProduct(request, productRequestDto);
    }
}
