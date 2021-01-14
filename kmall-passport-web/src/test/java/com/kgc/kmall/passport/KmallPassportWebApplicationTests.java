package com.kgc.kmall.passport;

import com.kgc.kmall.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class KmallPassportWebApplicationTests {

	@Test
	void contextLoads() {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId","1");
		map.put("nickname","zhangsan");
		String ip = "127.0.0.1";
		//String time = new SimpleDateFormat("yyyyMMdd HHmm").format(new Date());
		String encode = JwtUtil.encode("2020kmall075", map, ip);
		System.err.println(encode);
	}

	@Test
	void quma(){
		Map<String, Object> decode = JwtUtil.decode("eyJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6InpoYW5nc2FuIiwibWVtYmVySWQiOiIxIn0.eg_xHfMNMsiXCiAJAAA04sQXPwW8GfB7Q93w68mJkDc", "2020kmall075", "127.0.0.1");
		System.out.println(decode);
	}

}
