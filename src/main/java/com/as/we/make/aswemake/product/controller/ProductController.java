package com.as.we.make.aswemake.product.controller;

import com.as.we.make.aswemake.product.request.ProductCreateRequestDto;
import com.as.we.make.aswemake.product.request.ProductDeleteRequestDto;
import com.as.we.make.aswemake.product.request.ProductUpdateRequestDto;
import com.as.we.make.aswemake.product.service.ProductService;
import com.as.we.make.aswemake.share.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/awm/product")
@RestController
public class ProductController {

    private final ProductService productService;

    // 상품 생성
    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createProduct(HttpServletRequest request, @RequestBody ProductCreateRequestDto productCreateRequestDto){
        log.info("상품 생성 api - controller : 상품명 = {}, 상품 가격 = {}, 생성자 = {}", productCreateRequestDto.getProductName(), productCreateRequestDto.getPrice(), request);

        return productService.createProduct(request, productCreateRequestDto);
    }

    // 상품 가격 수정
    @PatchMapping("/update")
    public ResponseEntity<ResponseBody> updateProduct(HttpServletRequest request, @RequestBody ProductUpdateRequestDto productUpdateRequestDto){
        log.info("상품 가격 수정 api - controller : 상품 번호 = {}, 수정 가격 = {}", productUpdateRequestDto.getProductId(), productUpdateRequestDto.getPrice());

        return productService.updateProduct(request, productUpdateRequestDto);
    }

    // 상품 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody> deleteProduct(HttpServletRequest request, @RequestBody ProductDeleteRequestDto productDeleteRequestDto){
        log.info("상품 삭제 api - controller : 상품 번호 = {}, 삭제 요청 계정 = {}", productDeleteRequestDto.getProductId(), request);

        return productService.deleteProduct(request, productDeleteRequestDto);
    }

    // 상품 조회
    @GetMapping("/get")
    public ResponseEntity<ResponseBody> getProduct(@RequestParam Long productId, @RequestParam(required = false) String getDateTime){
        log.info("상품 조회 api - controller : 조회 상품 id = {}", productId);

        return productService.getProduct(productId, getDateTime);
    }

}
