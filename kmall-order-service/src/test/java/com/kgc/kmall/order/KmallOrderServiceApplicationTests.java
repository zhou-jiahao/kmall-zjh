package com.kgc.kmall.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class KmallOrderServiceApplicationTests {

	@Test
	void contextLoads() {
		String outTradeNo = "kmall";
		outTradeNo = outTradeNo + System.currentTimeMillis();// 将毫秒时间戳拼接到外部订单号
		System.out.println(outTradeNo);
		//将订单号和随机时间拼接
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
		outTradeNo = outTradeNo + sdf.format(new Date());// 将时间字符串拼接到外部订单号
		System.out.println(outTradeNo);
	}

}
