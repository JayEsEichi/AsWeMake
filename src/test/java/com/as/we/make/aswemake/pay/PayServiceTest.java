package com.as.we.make.aswemake.pay;


import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.coupon.domain.Coupon;
import com.as.we.make.aswemake.coupon.repository.CouponRepository;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.order.domain.Orders;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.order.request.OrderRequestDto;
import com.as.we.make.aswemake.order.response.OrderProductResponseVo;
import com.as.we.make.aswemake.order.service.OrderService;
import com.as.we.make.aswemake.pay.request.PayOrderRequestDto;
import com.as.we.make.aswemake.pay.response.PayResponseDto;
import com.as.we.make.aswemake.pay.service.PayService;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class PayServiceTest {

    @InjectMocks
    private PayService payService;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private TokenExceptionInterface tokenExceptionInterface;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CouponRepository couponRepository;



    @DisplayName("최종 결제 Service 테스트")
    @Test
    void payOrderTest() throws Exception {

        // given
        Account account = Account.builder()
                .accountId(1L)
                .accountEmail("test@naver.com")
                .accountPwd("test")
                .authority(Collections.singletonList("MART"))
                .build();

        accountRepository.save(account);

        AccountLoginRequestDto loginRequestDto = AccountLoginRequestDto.builder()
                .accountPwd(account.getAccountPwd())
                .accountEmail(account.getAccountEmail())
                .build();


//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.loginAccount(loginRequestDto, response);
//        MockHttpServletRequest request = new MockHttpServletRequest(new MockServletContext());
        HashMap<Product, Integer> products = new HashMap<>();

        for (int i = 1; i < 3; i++) {
            Product product = Product.builder()
                    .productId((long) i)
                    .productName("테스트 상품 " + i)
                    .price(3000)
                    .build();

            productRepository.save(product);

            products.put(product, 1);
        }

        Orders order = Orders.builder()
                .ordersId(1L)
                .deliveryPay(5000)
                .totalPrice(6000)
                .account(account)
                .products(products)
                .build();

        orderRepository.save(order);

        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .couponType("FIX")
                .discountContent(2000)
                .build();

        couponRepository.save(coupon);

        PayOrderRequestDto payOrderRequestDto = PayOrderRequestDto.builder()
                .ordersId(1L)
                .paymentCost(15000)
                .couponWhether("O")
                .couponId(1L)
                .couponScope("ALL")
                .specificProductId(0L)
                .build();

        PayResponseDto payResponseDto = PayResponseDto.builder()
                .totalPrice(9000)
                .paymentCost(payOrderRequestDto.getPaymentCost())
                .remainCost(payOrderRequestDto.getPaymentCost() - 9000)
                .couponWhether("O")
                .discountPrice((int) coupon.getDiscountContent())
                .build();


        // 계정 생성 후 반환되는 상태 코드 값
        int statusCode = payService.payOrder(any(MockHttpServletRequest.class), payOrderRequestDto).getBody().getStatusCode();

        // then
        // 상태 코드 값이 정상처리된 200인지 확인
        assertThat(statusCode).isEqualTo(200);

    }


}
