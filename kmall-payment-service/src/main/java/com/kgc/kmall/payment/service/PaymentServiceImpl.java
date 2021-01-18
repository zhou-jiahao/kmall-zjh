package com.kgc.kmall.payment.service;

import com.kgc.kmall.bean.PaymentInfo;
import com.kgc.kmall.bean.PaymentInfoExample;
import com.kgc.kmall.payment.mapper.PaymentInfoMapper;
import com.kgc.kmall.service.PaymentService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    PaymentInfoMapper paymentInfoMapper;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }

    @Override
    public void updatePayment(PaymentInfo paymentInfo) {
        //根据订单编号和主键修改id
        PaymentInfoExample example=new PaymentInfoExample();
        example.createCriteria().andOrderSnEqualTo(paymentInfo.getOrderSn());
        paymentInfoMapper.updateByExampleSelective(paymentInfo,example);
    }
}
