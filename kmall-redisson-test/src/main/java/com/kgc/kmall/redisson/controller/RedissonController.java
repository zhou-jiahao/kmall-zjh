package com.kgc.kmall.redisson.controller;


import com.kgc.kmall.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

@RestController
public class RedissonController {

//    @Resource
//    RedisUtil redisUtil;

    @Resource
    RedissonClient redissonClient;

    RedisUtil redisUtil=new RedisUtil();

    @RequestMapping("/test")
    public String testRedisson(){
        redisUtil.initPool("192.168.56.138",6379,0);
        Jedis jedis = redisUtil.getJedis();
        Lock lock = redissonClient.getLock("lock");//生命锁
        lock.lock();//上锁
        try {
            String v = jedis.get("k");
            if (v==null) {
                v = "1";
            }
            System.out.println("->" + v);
            jedis.set("k", (Integer.parseInt(v) + 1) + "");
        }finally {
            lock.unlock();
            jedis.close();
        }
        return "success";
    }

}
