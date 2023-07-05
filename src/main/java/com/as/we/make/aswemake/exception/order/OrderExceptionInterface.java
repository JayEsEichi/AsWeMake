package com.as.we.make.aswemake.exception.order;

public interface OrderExceptionInterface {

    /** 자기가 주문한 내역이 아닌 것은 결제 할 수 없음을 확인 **/
    boolean checkMyOrderBeforPay(Long ordersId, Long accountId);

    /** 주문 내역의 총 금액보다 지불 금액이 부족한지 확인 **/
    boolean checkPaymentCost(Integer orderTotalPrice, Integer paymentCost);

}
