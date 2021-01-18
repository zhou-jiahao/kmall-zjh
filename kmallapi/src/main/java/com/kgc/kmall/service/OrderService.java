package com.kgc.kmall.service;

import com.kgc.kmall.bean.Order;

public interface OrderService {

    String genTradeCode(Long memberId);

    String checkTradeCode(Long aLong, String tradeCode);

    void saveOrder(Order omsOrder);

    Order getOrderByOutTradeNo(String outTradeNo);
}
