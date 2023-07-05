package com.as.we.make.aswemake.pay;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.coupon.domain.Coupon;
import com.as.we.make.aswemake.coupon.repository.CouponRepository;
import com.as.we.make.aswemake.order.domain.Orders;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.order.request.OrderRequestDto;
import com.as.we.make.aswemake.order.response.OrderProductResponseVo;
import com.as.we.make.aswemake.order.response.OrderResponseDto;
import com.as.we.make.aswemake.pay.controller.PayController;
import com.as.we.make.aswemake.pay.repository.PayRepository;
import com.as.we.make.aswemake.pay.request.PayOrderRequestDto;
import com.as.we.make.aswemake.pay.response.PayResponseDto;
import com.as.we.make.aswemake.pay.service.PayService;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class PayControllerTest {

    @InjectMocks
    private PayController payController;

    @Mock
    private PayService payService;

    @Mock
    private PayRepository payRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(payController).build();
    }


    @DisplayName("최종 결제 Controller 테스트")
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


        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("결제 완료", payResponseDto)), HttpStatus.OK))
                .when(payService)
                .payOrder(any(HttpServletRequest.class), any(PayOrderRequestDto.class));

        String payRequest = new Gson().toJson(payOrderRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/awm/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNjg4NjA2MTk1fQ.gPC0VUZPlTcqUXli__q3VEQx4vJzaNSRgPOrgAIDmrw")
                        .characterEncoding("utf-8")
                        .content(payRequest));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultData.totalPrice").value(9000));

        System.out.println(resultActionsThen);
    }



    // 테스트 결과 확인 데이터
    private LinkedHashMap<String, Object> resultSet(String message, Object data) {

        LinkedHashMap<String, Object> resultSet = new LinkedHashMap<>();
        resultSet.put("resultMessage", message);
        resultSet.put("resultData", data);

        return resultSet;
    }

}
