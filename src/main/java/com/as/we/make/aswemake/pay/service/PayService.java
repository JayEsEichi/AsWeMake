package com.as.we.make.aswemake.pay.service;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.coupon.domain.Coupon;
import com.as.we.make.aswemake.coupon.repository.CouponRepository;
import com.as.we.make.aswemake.exception.order.OrderExceptionInterface;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.order.domain.Orders;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.pay.domain.PaymentDetails;
import com.as.we.make.aswemake.pay.repository.PayRepository;
import com.as.we.make.aswemake.pay.request.PayOrderRequestDto;
import com.as.we.make.aswemake.pay.response.PayResponseDto;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Service
public class PayService {

    private final TokenExceptionInterface tokenExceptionInterface;
    private final OrderExceptionInterface orderExceptionInterface;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final PayRepository payRepository;


    /** 등록된 주문 내역 결제 **/
    public ResponseEntity<ResponseBody> payOrder(HttpServletRequest request, PayOrderRequestDto payOrderRequestDto){

        // 요청 토큰 확인
        if (!tokenExceptionInterface.checkToken(request)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // 자기가 주문한 내역이 아닐 경우 결제할 수 없음
        if(orderExceptionInterface.checkMyOrderBeforPay(payOrderRequestDto.getOrdersId(), account.getAccountId())){
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_PAY_ORDER, null), HttpStatus.BAD_REQUEST);
        }

        // 결제할 주문 내역 조회
        Orders payOrder = orderRepository.findByOrdersId(payOrderRequestDto.getOrdersId())
                .orElseThrow(() -> new NullPointerException("존재하지 않는 주문 내역입니다."));

        // 주문 내역의 총 금액보다 지불 금액이 부족한지 확인
        if(orderExceptionInterface.checkPaymentCost(payOrder.getTotalPrice(), payOrderRequestDto.getPaymentCost())){
            return new ResponseEntity<>(new ResponseBody(StatusCode.SHORTAGE_OF_MONEY, null), HttpStatus.BAD_REQUEST);
        }

        // 최종 결제 정보를 담는 Dto 객체
        PayResponseDto payResponseDto;

        // 쿠폰을 사용할 경우
        if(payOrderRequestDto.getCouponWhether().equals("O")){
            // 최종적으로 결제될 금액
            int totalPrice = 0;
            // 사용할 쿠폰 조회
            Coupon coupon = couponRepository.findByCouponId(payOrderRequestDto.getCouponId());
            // 할인 금액
            int discountPrice = 0;

            // 쿠폰 적용 범위가 전체일 경우
            if(payOrderRequestDto.getCouponScope().equals("ALL")){

                // 쿠폰의 타입이 고정일 경우
                if(coupon.getCouponType().equals("FIX")){

                    // 고정 할인 금액
                    discountPrice = (int)coupon.getDiscountContent();
                    // 전체일 경우 배달비를 제외한 금액에서 고정 할인 비용 차감
                    totalPrice = (payOrder.getTotalPrice() - payOrder.getDeliveryPay()) - discountPrice;
                    // 할인된 이후 다시 배달비 추가
                    totalPrice += payOrder.getDeliveryPay();

                    // 쿠폰의 타입이 비율일 경우
                }else if(coupon.getCouponType().equals("RATIO")){

                    // 비율 할인 금액
                    discountPrice = ((payOrder.getTotalPrice() - payOrder.getDeliveryPay()) * (int)coupon.getDiscountContent());
                    // 전체일 경우 배달비를 제외한 금액
                    totalPrice = (payOrder.getTotalPrice() - payOrder.getDeliveryPay()) - discountPrice;
                    // 할인된 이후 다시 배달비 추가
                    totalPrice += payOrder.getDeliveryPay();
                }

                // 쿠폰 적용 범위가 특정 상품만일 경우
            }else if(payOrderRequestDto.getCouponScope().equals("SPECIFIC")){

                // 쿠폰의 타입이 고정일 경우
                if(coupon.getCouponType().equals("FIX")){

                    // 주문 내역에 저장된 상품들 정보 조회
                    for (Product eachProduct : payOrder.getProducts().keySet()) {

                        // 특정 상품만을 고정 가격 할인할 경우 해당 특정 상품의 가격과 개수를 곱한 가격에 고정 가격 할인
                        if(eachProduct.getProductId() == payOrderRequestDto.getSpecificProductId()){
                            discountPrice = (int)coupon.getDiscountContent();
                            totalPrice += eachProduct.getPrice() * payOrder.getProducts().get(eachProduct) - discountPrice;

                            // 특정 상품 이외의 할인이 적용되지 않는 상품들은 기존처럼 해당 상품의 가격과 개수를 곱한 가격으로 저장
                        }else{
                            totalPrice += eachProduct.getPrice() * payOrder.getProducts().get(eachProduct);
                        }
                    }

                    // 할인된 상품의 가격과 다른 상품들의 가격들을 합한 가격에서 최종적으로 배달비까지 추가
                    totalPrice += payOrder.getDeliveryPay();

                    // 쿠폰의 타입이 비율일 경우
                }else if(coupon.getCouponType().equals("RATIO")){

                    // 주문 내역에 저장된 상품들 정보 조회
                    for (Product eachProduct : payOrder.getProducts().keySet()) {

                        // 특정 상품만을 비율 할인할 경우 해당 특정 상품의 가격과 개수를 곱한 가격에 해당 비율만큼 가격 할인
                        if(eachProduct.getProductId() == payOrderRequestDto.getSpecificProductId()){
                            discountPrice = ((eachProduct.getPrice() * payOrder.getProducts().get(eachProduct)) * (int)coupon.getDiscountContent());
                            totalPrice += (eachProduct.getPrice() * payOrder.getProducts().get(eachProduct)) - discountPrice;

                            // 특정 상품 이외의 할인이 적용되지 않는 상품들은 기존처럼 해당 상품의 가격과 개수를 곱한 가격으로 저장
                        }else{
                            totalPrice += eachProduct.getPrice() * payOrder.getProducts().get(eachProduct);
                        }
                    }

                    // 할인된 상품의 가격과 다른 상품들의 가격들을 합한 가격에서 최종적으로 배달비까지 추가
                    totalPrice += payOrder.getDeliveryPay();
                }
            }

            // 결제 내역 정보 input
            PaymentDetails paymentDetails = PaymentDetails.builder()
                    .totalPrice(totalPrice)
                    .paymentCost(payOrderRequestDto.getPaymentCost())
                    .remainCost(payOrderRequestDto.getPaymentCost() - totalPrice)
                    .couponWhether("O")
                    .discountPrice(discountPrice)
                    .coupon(coupon)
                    .ordersId(payOrder.getOrdersId())
                    .build();

            // 결제 내역 저장
            payRepository.save(paymentDetails);

            // 결제할 주문 정보들에 쿠폰 할인이 적용된 결과를 확인하기 위한 Dto 객체
            payResponseDto = PayResponseDto.builder()
                    .totalPrice(totalPrice)
                    .paymentCost(payOrderRequestDto.getPaymentCost())
                    .remainCost(payOrderRequestDto.getPaymentCost() - totalPrice)
                    .couponWhether("O")
                    .discountPrice(discountPrice)
                    .build();

            // 쿠폰을 사용하지 않을 경우
        }else{

            // 결제 내역 정보 input
            PaymentDetails paymentDetails = PaymentDetails.builder()
                    .totalPrice(payOrder.getTotalPrice())
                    .paymentCost(payOrderRequestDto.getPaymentCost())
                    .remainCost(payOrderRequestDto.getPaymentCost() - payOrder.getTotalPrice())
                    .couponWhether("X")
                    .discountPrice(0)
                    .ordersId(payOrder.getOrdersId())
                    .build();

            // 결제 내역 저장
            payRepository.save(paymentDetails);

            // 결제할 주문 정보들에 쿠폰 할인이 적용되지 않았을 경우의 결과를 확인하기 위한 Dto 객체
            payResponseDto = PayResponseDto.builder()
                    .totalPrice(payOrder.getTotalPrice())
                    .paymentCost(payOrderRequestDto.getPaymentCost())
                    .remainCost(payOrderRequestDto.getPaymentCost() - payOrder.getTotalPrice())
                    .couponWhether("X")
                    .discountPrice(0)
                    .build();
        }

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("결제 되었습니다.", payResponseDto)), HttpStatus.OK);
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
