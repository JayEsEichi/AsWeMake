package com.as.we.make.aswemake.product;

import com.as.we.make.aswemake.account.controller.AccountController;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.response.AccountResponseDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.product.controller.ProductController;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.product.request.ProductRequestDto;
import com.as.we.make.aswemake.product.response.ProductResponseDto;
import com.as.we.make.aswemake.product.service.ProductService;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.util.LinkedHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @DisplayName("상품 생성 Controller 테스트")
    @Test
    void createProductTest() throws Exception {
        // given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .productName("마우스패드")
                .price(10000)
                .build();

        ProductResponseDto createProductDto = createProductDto(productRequestDto);

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품 생성", createProductDto)), HttpStatus.OK))
                .when(productService)
                .createProduct(any(HttpServletRequest.class), any(ProductRequestDto.class));

        String productRequestInfo = new Gson().toJson(productRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/awm/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJ0QG5hdmVyLmNvbSIsImF1dGgiOiJNQVJUIiwiZXhwIjoxNjg4NDM0NTQ3fQ.HFHrEqOjI2DZXIqGo1djiLPsJFu4iMnhbBX1kFL15Kg")
                        .characterEncoding("utf-8")
                        .content(productRequestInfo));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultData.productName").value(productRequestDto.getProductName()))
                .andExpect(jsonPath("$.data.resultData.price").value(productRequestDto.getPrice()));

        System.out.println(resultActionsThen);
    }

    // 상품 생성 시 정상적으로 출력되어야 할 데이터
    private ProductResponseDto createProductDto(ProductRequestDto productRequestDto){
        return ProductResponseDto.builder()
                .productName(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .build();
    }


    // 테스트 결과 확인 데이터
    private LinkedHashMap<String, Object> resultSet(String message, Object data){

        LinkedHashMap<String, Object> resultSet = new LinkedHashMap<>();
        resultSet.put("resultMessage", message);
        resultSet.put("resultData", data);

        return resultSet;
    }

}
