package com.as.we.make.aswemake.product.service;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.exception.account.AccountExceptionInterface;
import com.as.we.make.aswemake.exception.token.TokenExceptionInterface;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import com.as.we.make.aswemake.order.domain.Orders;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import com.as.we.make.aswemake.product.domain.Product;
import com.as.we.make.aswemake.product.domain.ProductUpdateDetails;
import com.as.we.make.aswemake.product.repository.ProductRepository;
import com.as.we.make.aswemake.product.repository.ProductUpdateDetailsRepository;
import com.as.we.make.aswemake.product.request.ProductCreateRequestDto;
import com.as.we.make.aswemake.product.request.ProductUpdateRequestDto;
import com.as.we.make.aswemake.product.response.ProductResponseDto;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final TokenExceptionInterface tokenExceptionInterface;
    private final AccountExceptionInterface accountExceptionInterface;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductUpdateDetailsRepository productUpdateDetailsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EntityManager entityManager;


    /**
     * 상품 생성
     **/
    public ResponseEntity<ResponseBody> createProduct(HttpServletRequest request, ProductCreateRequestDto productCreateRequestDto) {

        // 요청 토큰 확인
        if (!tokenExceptionInterface.checkToken(request)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // MART 권한을 가진 계정만 상품 관리 가능
        if (!accountExceptionInterface.checkAuthority(account.getAuthority().get(0))) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_POSSIBLE_AUTHORITY, null), HttpStatus.BAD_REQUEST);
        }

        // 생성할 상품 정보 input
        Product product = Product.builder()
                .productName(productCreateRequestDto.getProductName())
                .price(productCreateRequestDto.getPrice())
                .account(account)
                .build();

        // 상품 생성
        productRepository.save(product);

        // 상품을 생성하면 상품 정보 이력에도 저장
        ProductUpdateDetails productUpdateDetails = ProductUpdateDetails.builder()
                .price(product.getPrice()) // 생성했을 당시의 가격
                .productName(product.getProductName()) // 생성했을 당시의 이름
                .updateTime(product.getCreatedAt()) // 생성했을 당시의 날짜
                .product(product)
                .build();

        // 상품 정보 이력 저장
        productUpdateDetailsRepository.save(productUpdateDetails);

        // 생성 확인을 위한 ResponseDto 객체에 정보 저장
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("상품 생성이 완료되었습니다.", productResponseDto)), HttpStatus.OK);
    }


    /**
     * 상품 가격 수정
     **/
    @Transactional
    public ResponseEntity<ResponseBody> updateProduct(HttpServletRequest request, ProductUpdateRequestDto productUpdateRequestDto) {

        // 요청 토큰 확인
        if (!tokenExceptionInterface.checkToken(request)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // MART 권한을 가진 계정만 상품 관리 가능
        if (!accountExceptionInterface.checkAuthority(account.getAuthority().get(0))) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_POSSIBLE_AUTHORITY, null), HttpStatus.BAD_REQUEST);
        }

        Product updateProduct = productRepository.findByAccountAndProductId(account, productUpdateRequestDto.getProductId())
                .orElseThrow(() -> new NullPointerException("수정 요청한 계정의 상품이 아니라서 수정할 수 없습니다."));

        // 가격 수정
        updateProduct.setPrice(productUpdateRequestDto.getPrice());

        // 영속성 컨텍스트에 반영된 트랜잭션 데이터를 즉시 반영하여 수정일자 업데이트
        entityManager.flush();
        entityManager.clear();

        // 상품 변경 이력 정보 추가
        ProductUpdateDetails productUpdateDetails = ProductUpdateDetails.builder()
                .price(updateProduct.getPrice())
                .productName(updateProduct.getProductName())
                .updateTime(updateProduct.getModifiedAt()) // 상품 정보 이력에 업데이트된 수정 일자를 넣어 추가
                .product(updateProduct)
                .build();

        // 추가된 상품 이력 정보 저장
        productUpdateDetailsRepository.save(productUpdateDetails);

        // 수정 확인을 위한 ResponseDto 객체에 정보 저장
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productName(updateProduct.getProductName())
                .price(updateProduct.getPrice())
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet("정상적으로 수정되었습니다.", productResponseDto)), HttpStatus.OK);
    }


    /**
     * 상품 삭제
     **/
    @Transactional
    public ResponseEntity<ResponseBody> deleteProduct(HttpServletRequest request, Long productId) {

        // 요청 토큰 확인
        if (!tokenExceptionInterface.checkToken(request)) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.NOT_CORRECT_TOKEN, null), HttpStatus.BAD_REQUEST);
        }

        // 로그인 하여 ContextHolder에 저장된 인증된 유저 호출
        Account account = jwtTokenProvider.getMemberFromAuthentication();

        // MART 권한을 가진 계정만 상품 관리 가능
        if (!accountExceptionInterface.checkAuthority(account.getAuthority().get(0))) {
            return new ResponseEntity<>(new ResponseBody(StatusCode.CANNOT_POSSIBLE_AUTHORITY, null), HttpStatus.BAD_REQUEST);
        }

        // 삭제할 상품을 만든 계정이 맞는지 확인
        Product deleteProduct = productRepository.findByAccountAndProductId(account, productId)
                .orElseThrow(() -> new NullPointerException("삭제 요청한 계정이 생성한 상품이 아니라서 삭제할 수 없습니다."));

        // 전체 주문들을 조회
        for(Orders eachOrder : orderRepository.findAll()){
            // 만약 주문들 중에 지우고자 하는 상품이 포함되어있을 경우 우선적으로 주문 정보에서 해당 상품 삭제
            if(eachOrder.getProducts().containsKey(deleteProduct)){
                eachOrder.getProducts().remove(deleteProduct);

                // 만약 주문에 들어있는 상품을 삭제 후 주문에 상품이 존재하지 않게 되면 주문 자체를 삭제
                if(eachOrder.getProducts().isEmpty()){
                    orderRepository.deleteById(eachOrder.getOrdersId());
                }
            }
        }

        // 상품 및 상품 이력 삭제
        productUpdateDetailsRepository.deleteAllByProduct(deleteProduct);
        productRepository.deleteByAccountAndProductId(account, productId);

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, "정상적으로 삭제되었습니다."), HttpStatus.OK);
    }


    /**
     * 상품 조회
     **/
    public ResponseEntity<ResponseBody> getProduct(Long productId, String getDateTime) {

        // 조회하고자 하는 상품 조회
        Product getProduct = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 상품입니다."));

        // 조회 상품의 정보 이력들 조회 (가장 최신의 날짜부터 나오도록 정렬 기준을 desc로 지정)
        List<ProductUpdateDetails> productDetailsList = productUpdateDetailsRepository.findAllByProductOrderByUpdateTimeDesc(getProduct)
                .orElseThrow(() -> new NullPointerException("해당 상품에 대한 이력이 없습니다."));

        // 조회 시점의 가격
        int price = 0;

        // 조회하고자 하는 특정 시점이 존재할 경우
        if (getDateTime != null) {
            // 특정 시점 날짜 데이터를 비교하기 위해 format을 돌려 localdatetime으로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime viewDateTime = LocalDateTime.parse(getDateTime, formatter);

            // 조회한 상품 정보 이력들 조회
            for (int i = 0; i < productDetailsList.size(); i++) {

                // 만약 특정 조회 시점과 완전히 일치하는 날짜라면 그 날짜의 데이터값을 가져옴
                if(productDetailsList.get(i).getUpdateTime().isEqual(viewDateTime)){
                    price = productDetailsList.get(i).getPrice();
                    break;
                }

                // 가장 오래된 상품 정보 이력이 아닐 경우
                if (i != productDetailsList.size() - 1) {
                    // 조회한 정보 이력들 중에서 조회하고자 하는 특정 조회 시점의 날짜보다 최신인 이력이고 다음 정보이력의 날짜가 조회 시점의 날짜보다 이전이면 그 시점의 가격 조회
                    if (productDetailsList.get(i).getUpdateTime().isAfter(viewDateTime) && productDetailsList.get(i + 1).getUpdateTime().isBefore(viewDateTime)) {
                        price = productDetailsList.get(i + 1).getPrice();
                        break;
                    }
                }
            }

        // 특정 시점이 존재하지 않을 경우 가장 최신 시점의 가격을 조회
        } else {
            price = productDetailsList.get(0).getPrice();
            getDateTime = "최근";
        }

        // 결과를 확인하기 위한 ResponseDto 객체 생성
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productName(getProduct.getProductName())
                .price(price)
                .build();

        return new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, resultSet(getProduct.getProductName() + " 상품의 " + getDateTime + " 시점의 가격은 " + price + "원 입니다.", productResponseDto)), HttpStatus.OK);
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
