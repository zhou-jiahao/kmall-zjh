package com.kgc.kmall.order.service;

import com.kgc.kmall.bean.Order;
import com.kgc.kmall.bean.OrderExample;
import com.kgc.kmall.bean.OrderItem;
import com.kgc.kmall.order.mapper.OrderItemMapper;
import com.kgc.kmall.order.mapper.OrderMapper;
import com.kgc.kmall.service.OrderService;
import com.kgc.kmall.util.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Component
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    RedisUtil redisUtil;

    @Override
    public String genTradeCode(Long memberId) {
        //连接jedis
        Jedis jedis = redisUtil.getJedis();
        //自定义交易码的键
        String tradeKey = "user:"+memberId+":tradeCode";
        //随机生成交易码
        String tradeCode = UUID.randomUUID().toString();
        //存入缓存
        jedis.setex(tradeKey,60*15,tradeCode);
        //关闭jedis
        jedis.close();
        //返回 交易码
        return tradeCode;
    }

    @Override
    public String checkTradeCode(Long aLong, String tradeCode) {
        //连接jedis
        Jedis jedis = redisUtil.getJedis();
        //自定义交易码的键
        String tradeKey = "user:"+aLong+":tradeCode";
        String code = jedis.get(tradeKey);
        if(code!=null && code.equals(tradeCode)){
            jedis.del(tradeKey);
            return "success";
        }else{
            return "fail";
        }
    }
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderItemMapper orderItemMapper;

    @Override
    public void saveOrder(Order order) {
        // 保存订单表
        orderMapper.insertSelective(order);
        Long orderId = order.getId();
        // 保存订单详情
        List<OrderItem> omsOrderItems = order.getOrderItems();
        for (OrderItem orderItem : omsOrderItems) {
            //循环把订单详情绑定订单对象id
            orderItem.setOrderId(orderId);
            //添加订单详情
            orderItemMapper.insertSelective(orderItem);
            // 删除购物车数据,暂时不进行购物车删除，因为需要频繁的测试
            // cartService.delCart();
        }
    }

    @Override
    public Order getOrderByOutTradeNo(String outTradeNo) {
        OrderExample example=new OrderExample();
        //根据订单编号查询这条订单是否存在
        example.createCriteria().andOrderSnEqualTo(outTradeNo);
        List<Order> orders = orderMapper.selectByExample(example);
        if (orders!=null&&orders.size()>0)
            return  orders.get(0);
        else
            return null;
    }
}
