package com.as.we.make.aswemake.order.controller;

import com.as.we.make.aswemake.order.request.OrderRequestDto;
import com.as.we.make.aswemake.order.service.OrderService;
import com.as.we.make.aswemake.share.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/awm/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    // 상품 주문
    @PostMapping("/ordering")
    public ResponseEntity<ResponseBody> orderProduct(HttpServletRequest request, @RequestBody List<OrderRequestDto> orderRequestDtos){
        log.info("상품 주문 api - controller : 주문자 = {}, 주문 상품 개수 = {}", request,orderRequestDtos.size());

        return orderService.orderProduct(request, orderRequestDtos);
    }
}
