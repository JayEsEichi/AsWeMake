package com.as.we.make.aswemake.exception.order;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderException implements OrderExceptionInterface {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    /** 자기가 주문한 내역이 아닌 것은 결제 할 수 없음을 확인 **/
    @Override
    public boolean checkMyOrderBeforPay(Long ordersId, Long accountId) {

        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 계정입니다."));

        if(orderRepository.findByOrdersIdAndAccount(ordersId, account).isEmpty()){
            return true;
        }

        return false;
    }

    /** 주문 내역의 총 금액보다 지불 금액이 부족한지 확인 **/
    @Override
    public boolean checkPaymentCost(Integer orderTotalPrice, Integer paymentCost) {

        if(orderTotalPrice > paymentCost){
            return true;
        }

        return false;
    }
}
