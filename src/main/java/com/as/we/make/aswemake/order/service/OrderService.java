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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final TokenExceptionInterface tokenExceptionInterface;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * 상품 주문 등록
     **/
    public ResponseEntity<ResponseBody> orderProduct(HttpServletRequest request, List<OrderRequestDto> orderRequestDtos) {

        // 요청 토큰 확인
        if (!tokenExceptionInterface.checkToken(request)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // 주문 등록할 상품과 개수가 저장될 HasgMap.
        // Orders 엔티티에 저장되면서 동시에 @ElementCollection 어노테이션으로 별도의 테이블에 저장되어 관리.
        HashMap<Product, Integer> orderProductList = new HashMap<>();
        // 주문 등록한 상품들의 정보들을 담는 ResponseVO 객체로 이루어진 List
        List<OrderProductResponseVo> orderProductsInfoList = new ArrayList<>();

        // 등록 요청한 상품들의 정보 저장
        for (OrderRequestDto productRequest : orderRequestDtos) {

            // 주문할 상품 조회
            Product orderProduct = productRepository.findByProductId(productRequest.getProductId())
                    .orElseThrow(() -> new NullPointerException("존재하지 않는 상품입니다."));

            // 주문 등록 상품과 개수 저장
            orderProductList.put(orderProduct, productRequest.getProductCount());

            // 반환받아 결과를 확인하기 위한 List에 등록된 상품 정보들 저장
            orderProductsInfoList.add(
                    OrderProductResponseVo.builder()
                            .productId(orderProduct.getProductId())
                            .productName(orderProduct.getProductName())
                            .price(orderProduct.getPrice())
                            .productCount(productRequest.getProductCount())
                            .build()
            );
        }

        // 주문 정보 엔티티 정보 기입
        Orders orders = Orders.builder()
                .deliveryPay(5000) // 배달비 5000원 고정
                .totalPrice(0) // 초기 주문 등록은 장바구니 처럼 등록, 즉, 담아놓기만 한 상태이므로 총 금액은 처음에 0원
                .account(account)
                .products(orderProductList)
                .build();

        // 주문 정보 저장
        orderRepository.save(orders);

        // 최종적으로 반환되어 확인될 Dto 객체 생성 및 정보 저장
        OrderResponseDto orderResponseData = OrderResponseDto.builder()
                .accountEmail(account.getAccountEmail())
                .deliveryPay(orders.getDeliveryPay())
                .orderProductList(orderProductsInfoList)
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품이 주문 등록된 상태입니다.", orderResponseData)), HttpStatus.OK);
    }


    /**
     * 주문할 상품들 총 금액 계산 및 조회 (주문 내용 확정 - 총 금액 업데이트)
     **/
    @Transactional
    public ResponseEntity<ResponseBody> calculateTotalOrderPrice(Long ordersId) {

        // 총액을 구하고자 하는 주문 내역 조회
        Orders order = orderRepository.findByOrdersId(ordersId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 주문 내역입니다."));

        // 주문 내역에 포함되어 있는 상품들 호출
        Map<Product, Integer> orderProducts = order.getProducts();
        // 총 금액 중 배달비 우선 포함
        int totalPrice = order.getDeliveryPay();

        // 주문 상품들의 가격과 개수를 곱하여 나온 가격을 totalPrice 변수에 더하기
        for (Product eachProduct : orderProducts.keySet()) {
            totalPrice += eachProduct.getPrice() * orderProducts.get(eachProduct);
        }

        // 현 api 는 주문 등록된 내용들을 계산 및 조회하면서 확정 짓는 api 이므로 총 금액을 Orders 엔티티에 반영
        order.setTotalPrice(totalPrice);

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("요청한 주문의 총 금액", totalPrice)), HttpStatus.OK);
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
