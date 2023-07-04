package com.as.we.make.aswemake.order;

import com.as.we.make.aswemake.order.controller.OrderController;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.order.request.OrderRequestDto;
import com.as.we.make.aswemake.order.response.OrderProductResponseVo;
import com.as.we.make.aswemake.order.response.OrderResponseDto;
import com.as.we.make.aswemake.order.service.OrderService;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class OrdersControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }


    @DisplayName("주문 등록 Controller 테스트")
    @Test
    void ordersProductTest() throws Exception {
        // given
        List<OrderProductResponseVo> orderProductsInfoList = new ArrayList<>();

        // 상품들 주문 저장
        for(int i = 0 ; i < 2 ; i++){

            orderProductsInfoList.add(
                    OrderProductResponseVo.builder()
                            .productId((long)(i+1))
                            .productName("테스트 상품")
                            .price(3000 * (i + 1))
                            .productCount(3 + i)
                            .build()
            );
        }

        OrderResponseDto orderResponseData = OrderResponseDto.builder()
                .accountEmail("test@naver.com")
                .deliveryPay(5000)
                .orderProductList(orderProductsInfoList)
                .build();

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("주문 등록 테스트", orderResponseData)), HttpStatus.OK))
                .when(orderService)
                .orderProduct(any(HttpServletRequest.class), any(List.class));

        String orderRequestDtos = new Gson().toJson(orderRequestDtos());

        System.out.println(orderRequestDtos);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/awm/order/ordering")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNjg4NTI1ODI4fQ.4tAm3JD10hu42km_cRHbop2oKEu1YDAkcLTfRLK-u7k")
                        .characterEncoding("utf-8")
                        .content(orderRequestDtos));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultData.orderProductList[0].productId").value(orderRequestDtos().get(0).getProductId()));

        System.out.println(resultActionsThen);
    }

    // 다중 상품 주문 등록 요청 (가정)
    private List<OrderRequestDto> orderRequestDtos(){

        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();

        for(int i = 0 ; i < 2 ; i++){
            orderRequestDtoList.add(
                    OrderRequestDto.builder()
                            .productCount(i)
                            .productId((long)(i + 1))
                            .build()
            );
        }

        return orderRequestDtoList;
    }


    // 테스트 결과 확인 데이터
    private LinkedHashMap<String, Object> resultSet(String message, Object data){

        LinkedHashMap<String, Object> resultSet = new LinkedHashMap<>();
        resultSet.put("resultMessage", message);
        resultSet.put("resultData", data);

        return resultSet;
    }

}
