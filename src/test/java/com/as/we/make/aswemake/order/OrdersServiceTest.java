package com.as.we.make.aswemake.order;


import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.order.domain.Orders;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.order.request.OrderRequestDto;
import com.as.we.make.aswemake.order.response.OrderProductResponseVo;
import com.as.we.make.aswemake.order.service.OrderService;
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private TokenExceptionInterface tokenExceptionInterface;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private EntityManager entityManager;


    @DisplayName("주문 등록 Service 테스트")
    @Test
    void orderProductTest() throws Exception {

        // 테스트 계정 생성
        Account account = Account.builder()
                .accountId(1L)
                .accountEmail("test@naver.com")
                .accountPwd("testpwd")
                .build();

        accountRepository.save(account);

        Product product;

        for(int i = 0 ; i < 2 ; i++) {
            product = Product.builder()
                    .productId((long) (i + 1))
                    .productName("테스트 상품" + (i + 1))
                    .price(2000)
                    .account(account)
                    .build();

            productRepository.save(product);
        }

//        System.out.println(product.getProductId());

        HashMap<Product, Integer> orderProductList = new HashMap<>();
        List<OrderProductResponseVo> orderProductsInfoList = new ArrayList<>();

        // 상품들 주문 저장
        for(OrderRequestDto productRequest : orderRequestDtos()){

            // 주문할 상품 조회
            Product orderProduct = productRepository.findByProductId(productRequest.getProductId())
                    .orElseThrow(() -> new NullPointerException("존재하지 않는 상품입니다."));

//            Product testProducy = entityManager.find(Product.class, productRequest.getProductId());

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

        MockHttpServletRequest request = new MockHttpServletRequest();

        // 계정 생성 후 반환되는 상태 코드 값
        int statusCode = orderService.orderProduct(request, orderRequestDtos()).getBody().getStatusCode();

        // then
        // 상태 코드 값이 정상처리된 200인지 확인
        assertThat(statusCode).isEqualTo(200);

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

}
