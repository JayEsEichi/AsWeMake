package com.as.we.make.aswemake.product;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.product.controller.ProductController;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.domain.ProductUpdateDetails;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.product.repository.ProductUpdateDetailsRepository;
import com.as.we.make.aswemake.product.request.ProductCreateRequestDto;
import com.as.we.make.aswemake.product.request.ProductUpdateRequestDto;
import com.as.we.make.aswemake.product.response.ProductResponseDto;
import com.as.we.make.aswemake.product.service.ProductService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductUpdateDetailsRepository productUpdateDetailsRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @DisplayName("상품 생성 Controller 테스트")
    @Test
    void createProductTest() throws Exception {
        // given
        ProductCreateRequestDto productCreateRequestDto = ProductCreateRequestDto.builder()
                .productName("마우스패드")
                .price(10000)
                .build();

        ProductResponseDto createProductDto = productResponseDto(productCreateRequestDto);

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품 생성", createProductDto)), HttpStatus.OK))
                .when(productService)
                .createProduct(any(HttpServletRequest.class), any(ProductCreateRequestDto.class));

        String productRequestInfo = new Gson().toJson(productCreateRequestDto);

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
                .andExpect(jsonPath("$.data.resultData.productName").value(productCreateRequestDto.getProductName()))
                .andExpect(jsonPath("$.data.resultData.price").value(productCreateRequestDto.getPrice()));

        System.out.println(resultActionsThen);
    }


    @DisplayName("상품 수정 Controller 테스트")
    @Test
    void updateProductTest() throws Exception {
        // given
        Account account = Account.builder()
                .accountId(3L)
                .accountEmail("test@naver.com")
                .accountPwd("test0000!!!!")
                .authority(Collections.singletonList("MART"))
                .build();

        Product product = Product.builder()
                .productId(2L)
                .productName("테스트 상품")
                .price(10000)
                .account(account)
                .build();

        productRepository.save(product);

        ProductUpdateRequestDto productUpdateRequestDto = ProductUpdateRequestDto.builder()
                .productId(2L)
                .price(90000)
                .build();

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품 수정", productUpdateRequestDto)), HttpStatus.OK))
                .when(productService)
                .updateProduct(any(HttpServletRequest.class), any(ProductUpdateRequestDto.class));

        String productUpdateRequestInfo = new Gson().toJson(productUpdateRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/awm/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJ0QG5hdmVyLmNvbSIsImF1dGgiOiJNQVJUIiwiZXhwIjoxNjg4NDUxODYyfQ.Ll1hqSIX7PzsvfBF4PbgR5ilIM9SQh-f9WpWA7GAWYo")
                        .characterEncoding("utf-8")
                        .content(productUpdateRequestInfo));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultData.productId").value(productUpdateRequestDto.getProductId()))
                .andExpect(jsonPath("$.data.resultData.price").value(productUpdateRequestDto.getPrice()));

    }


    @DisplayName("상품 삭제 Controller 테스트")
    @Test
    void deleteProductTest() throws Exception {
        // given
        Account account = Account.builder()
                .accountId(3L)
                .accountEmail("test@naver.com")
                .accountPwd("test0000!!!!")
                .authority(Collections.singletonList("MART"))
                .build();

        Product product = Product.builder()
                .productId(2L)
                .productName("테스트 상품")
                .price(10000)
                .account(account)
                .build();

        productRepository.save(product);

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, "상품 삭제"), HttpStatus.OK))
                .when(productService)
                .deleteProduct(any(HttpServletRequest.class), any(Long.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/awm/product/delete?productId=" + 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJ0QG5hdmVyLmNvbSIsImF1dGgiOiJNQVJUIiwiZXhwIjoxNjg4NDUxODYyfQ.Ll1hqSIX7PzsvfBF4PbgR5ilIM9SQh-f9WpWA7GAWYo")
                        .characterEncoding("utf-8"));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("상품 조회 Controller 테스트")
    @Test
    void getProductTest() throws Exception {
        // given
        Product product = Product.builder()
                .productId(1L)
                .productName("테스트 상품")
                .price(10000)
                .build();

        productRepository.save(product);

        ProductUpdateDetails productUpdateDetails = ProductUpdateDetails.builder()
                .productUpdateDetailsId(1L)
                .price(2000)
                .productName(product.getProductName())
                .updateTime(LocalDateTime.now())
                .build();

        productUpdateDetailsRepository.save(productUpdateDetails);

        Thread.sleep(30);

        ProductUpdateDetails productUpdateDetails2 = ProductUpdateDetails.builder()
                .productUpdateDetailsId(1L)
                .price(9000)
                .productName(product.getProductName())
                .updateTime(LocalDateTime.now())
                .build();

        productUpdateDetailsRepository.save(productUpdateDetails2);

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productName(product.getProductName())
                .price(productUpdateDetails2.getPrice())
                .build();

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("조회 테스트", productResponseDto)), HttpStatus.OK))
                .when(productService)
                .getProduct(any(Long.class), any(String.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/awm/product/get?productId=" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultData.price").value(productUpdateDetails2.getPrice()));

    }


    // 상품 생성 시 정상적으로 출력되어야 할 데이터
    private ProductResponseDto productResponseDto(ProductCreateRequestDto productCreateRequestDto) {
        return ProductResponseDto.builder()
                .productName(productCreateRequestDto.getProductName())
                .price(productCreateRequestDto.getPrice())
                .build();
    }


    // 테스트 결과 확인 데이터
    private LinkedHashMap<String, Object> resultSet(String message, Object data) {

        LinkedHashMap<String, Object> resultSet = new LinkedHashMap<>();
        resultSet.put("resultMessage", message);
        resultSet.put("resultData", data);

        return resultSet;
    }

}
