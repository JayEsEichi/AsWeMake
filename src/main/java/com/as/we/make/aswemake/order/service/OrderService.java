package com.as.we.make.aswemake.order.service;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.order.domain.Orders;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.order.request.OrderRequestDto;
import com.as.we.make.aswemake.order.response.OrderProductResponseVo;
import com.as.we.make.aswemake.order.response.OrderResponseDto;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final TokenExceptionInterface tokenExceptionInterface;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 상품 주문
    public ResponseEntity<ResponseBody> orderProduct(HttpServletRequest request, List<OrderRequestDto> orderRequestDtos){

        // 요청 토큰 확인
        if(!tokenExceptionInterface.checkToken(request)){
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        HashMap<Product, Integer> orderProductList = new HashMap<>();
        List<OrderProductResponseVo> orderProductsInfoList = new ArrayList<>();

        // 상품들 주문 저장
        for(OrderRequestDto productRequest : orderRequestDtos){

            // 주문할 상품 조회
            Product orderProduct = productRepository.findByProductId(productRequest.getProductId())
                    .orElseThrow(() -> new NullPointerException("존재하지 않는 상품입니다."));

            orderProductList.put(orderProduct, productRequest.getProductCount());
            orderProductsInfoList.add(
                    OrderProductResponseVo.builder()
                            .productId(orderProduct.getProductId())
                            .productName(orderProduct.getProductName())
                            .price(orderProduct.getPrice())
                            .productCount(productRequest.getProductCount())
                            .build()
            );
        }

        // 주문 정보 엔티티 input
        Orders orders = Orders.builder()
                .deliveryPay(5000) // 배달비 5000원 고정
                .account(account)
                .products(orderProductList)
                .build();

        orderRepository.save(orders);

        OrderResponseDto orderResponseData = OrderResponseDto.builder()
                .accountEmail(account.getAccountEmail())
                .deliveryPay(orders.getDeliveryPay())
                .orderProductList(orderProductsInfoList)
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품이 주문 등록된 상태입니다.", orderResponseData)), HttpStatus.OK);
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
