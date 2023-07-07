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
    public ResponseEntity<ResponseBody> orderProduct(HttpServletRequest request, @RequestPart List<OrderRequestDto> orderRequestDtos){
        log.info("상품 주문 api - controller : 주문자 = {}, 주문 상품 개수 = {}", request,orderRequestDtos.size());

        return orderService.orderProduct(request, orderRequestDtos);
    }


    // 주문할 상품들 총 금액 계산 및 조회 (주문 내용 확정 - 총 금액 업데이트)
    @PatchMapping("/calculate")
    public ResponseEntity<ResponseBody> calculateTotalOrderPrice(@RequestParam Long ordersId){
        log.info("주문 상품들 총 금액 계산 조회 api - controller : 조회 주문 = {}", ordersId);

        return orderService.calculateTotalOrderPrice(ordersId);
    }
}
