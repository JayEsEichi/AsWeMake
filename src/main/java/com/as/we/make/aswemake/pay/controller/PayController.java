package com.as.we.make.aswemake.pay.controller;

import com.as.we.make.aswemake.pay.request.PayOrderRequestDto;
import com.as.we.make.aswemake.pay.service.PayService;
import com.as.we.make.aswemake.share.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/awm")
@RestController
public class PayController {

    private final PayService payService;

    // 등록된 주문 내역 결제
    @PostMapping("/pay")
    public ResponseEntity<ResponseBody> payOrder(HttpServletRequest request, @RequestBody PayOrderRequestDto payOrderRequestDto){
        log.info("주문 내역 결제 api - controller : 결제자 = {}, 결제 주문 내역 id = {}, 결제 지불 금액 = {}", request, payOrderRequestDto.getOrdersId(), payOrderRequestDto.getPaymentCost());

        return payService.payOrder(request, payOrderRequestDto);
    }
}
