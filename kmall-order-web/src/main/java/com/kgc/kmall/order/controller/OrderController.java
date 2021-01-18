package com.kgc.kmall.order.controller;

import com.kgc.kmall.annotations.LoginRequired;
import com.kgc.kmall.bean.MemberReceiveAddress;
import com.kgc.kmall.bean.OmsCartItem;
import com.kgc.kmall.bean.Order;
import com.kgc.kmall.bean.OrderItem;
import com.kgc.kmall.service.CartService;
import com.kgc.kmall.service.MemberService;
import com.kgc.kmall.service.OrderService;
import com.kgc.kmall.service.SkuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Reference
    MemberService memberService;

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;

    @Reference
    OrderService orderService;

    @LoginRequired(true)
    @RequestMapping("/toTrade")
    public String toTrade(HttpServletRequest request, Model model){
        //从拦截器中获取用户memberid和nickname
        Integer memberId = (Integer) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        System.out.println(memberId);
        System.out.println(nickname);

        // 收件人地址列表
        List<MemberReceiveAddress> umsMemberReceiveAddresses = memberService.getReceiveAddressByMemberId(Long.valueOf(memberId));
        model.addAttribute("userAddressList",umsMemberReceiveAddresses);


        // 将购物车集合转化为页面计算清单集合
        List<OmsCartItem> omsCartItems = cartService.cartList(memberId.toString());
        List<OrderItem> omsOrderItems = new ArrayList<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            // 每循环一个购物车对象，就封装一个商品的详情到OmsOrderItem
            if (omsCartItem.getIsChecked()==memberId) {
                OrderItem omsOrderItem = new OrderItem();
                omsOrderItem.setProductName(omsCartItem.getProductName());
                omsOrderItem.setProductPic(omsCartItem.getProductPic());
                omsOrderItems.add(omsOrderItem);
            }
        }

        model.addAttribute("omsOrderItems", omsOrderItems);
        model.addAttribute("totalAmount", getTotalAmount(omsCartItems,memberId));

        //生成交易码
        String tradeCode = orderService.genTradeCode(Long.valueOf(memberId));
        System.out.println(tradeCode);
        model.addAttribute("tradeCode", tradeCode);

        return "trade";
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems,Integer memberId) {
        BigDecimal totalAmount = new BigDecimal("0");

        for (OmsCartItem omsCartItem : omsCartItems) {
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(new BigDecimal(omsCartItem.getQuantity())));
            BigDecimal totalPrice = omsCartItem.getTotalPrice();

            if (omsCartItem.getIsChecked()==memberId) {
                totalAmount = totalAmount.add(totalPrice);
            }
        }

        return totalAmount;
    }

    @RequestMapping("submitOrder")
    @LoginRequired(value = true)
    public String submitOrder(String receiveAddressId, BigDecimal totalAmount, String tradeCode, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

        System.out.println(receiveAddressId);
        System.out.println(totalAmount);

        //从拦截器中获取用户memberid和nickname
        Integer memberId = (Integer) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");

        // 检查交易码
        String success = orderService.checkTradeCode(Long.valueOf(memberId), tradeCode);
        if(success.equals("success")) {
            System.out.println("提交订单");
            System.out.println(receiveAddressId);
            System.out.println(totalAmount);

            //声明一个订单详情对象
            List<OrderItem> omsOrderItems = new ArrayList<>();
            // 订单对象
            Order omsOrder = new Order();
            //自动确认时间（天）
            omsOrder.setAutoConfirmDay(7);
            //提交时间
            omsOrder.setCreateTime(new Date());
            //折扣金额
            omsOrder.setDiscountAmount(null);
            //omsOrder.setFreightAmount(); 运费，支付后，在生成物流信息时
            //用户id
            omsOrder.setMemberId(Long.valueOf(memberId));
            //用户昵称
            omsOrder.setMemberUsername(nickname);
            //备注
            omsOrder.setNote("快点发货");

            String outTradeNo = "kmall";
            outTradeNo = outTradeNo + System.currentTimeMillis();// 将毫秒时间戳拼接到外部订单号
            System.out.println(outTradeNo);
            //将订单号和随机时间拼接
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
            outTradeNo = outTradeNo + sdf.format(new Date());// 将时间字符串拼接到外部订单号
            //订单编号
            omsOrder.setOrderSn(outTradeNo);//外部订单号
            //应付金额（实际支付金额）
            omsOrder.setPayAmount(totalAmount);
            //订单类型：0->正常订单；1->秒杀订单
            omsOrder.setOrderType(1);
            //根据选中的地址id查询地址信息
            MemberReceiveAddress umsMemberReceiveAddress = memberService.getReceiveAddressById(Long.parseLong(receiveAddressId));

            //城市
            omsOrder.setReceiverCity(umsMemberReceiveAddress.getCity());
            //详细地址
            omsOrder.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());
            //收货人姓名
            omsOrder.setReceiverName(umsMemberReceiveAddress.getName());
            //收货人电话
            omsOrder.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
            //收货人邮编
            omsOrder.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
            //省份/直辖市
            omsOrder.setReceiverProvince(umsMemberReceiveAddress.getProvince());
            //区
            omsOrder.setReceiverRegion(umsMemberReceiveAddress.getRegion());

            // 当前日期加一天，一天后配送
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,1);
            Date time = c.getTime();
            //确认收货时间
            omsOrder.setReceiveTime(time);
            //订单来源：0->PC订单；1->app订单
            omsOrder.setSourceType(0);
            //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
            omsOrder.setStatus(0);
            //订单类型：0->正常订单；1->秒杀订单
            omsOrder.setOrderType(0);
            //订单总金额
            omsOrder.setTotalAmount(totalAmount);


            // 根据用户id获得要购买的商品列表(购物车)，和总价格
            List<OmsCartItem> omsCartItems = cartService.cartList(memberId.toString());
            for (OmsCartItem omsCartItem : omsCartItems) {
                if(omsCartItem.getIsChecked()==1){
                    // 获得订单详情列表
                    OrderItem omsOrderItem = new OrderItem();
                    //检价  判断购物车价格 和 商品价格是否相同
                    boolean b = skuService.checkPrice(omsCartItem.getProductSkuId(),omsCartItem.getPrice());
                    //如果不同 下单失败
                    if (b == false) {
                        return "tradeFail";
                    }

                    // 验库存,远程调用库存系统
                    //商品图片
                    omsOrderItem.setProductPic(omsCartItem.getProductPic());
                    //商品名称
                    omsOrderItem.setProductName(omsCartItem.getProductName());
                    //商品编号
                    omsOrderItem.setOrderSn(outTradeNo);// 外部订单号，用来和其他系统进行交互，防止重复
                    //商品分类id
                    omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());
                    //商品价格
                    omsOrderItem.setProductPrice(omsCartItem.getPrice());
                    //商品总价
                    omsOrderItem.setRealAmount(omsCartItem.getTotalPrice());
                    //商品数量
                    omsOrderItem.setProductQuantity(omsCartItem.getQuantity());
                    //商品sku条码
                    omsOrderItem.setProductSkuCode("111111111111");
                    //skuId
                    omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());
                    //商品id
                    omsOrderItem.setProductId(omsCartItem.getProductId());
                    omsOrderItem.setProductSn("仓库对应的商品编号");// 在仓库中的skuId

                    //将封装好的对象添加到集合中
                    omsOrderItems.add(omsOrderItem);
                }
            }
            //将集合放入订单对象的setOrderItems字段中
            omsOrder.setOrderItems(omsOrderItems);

            // 将订单和订单详情写入数据库
            // 删除购物车的对应商品,暂时不进行删除，因为接下来需要频繁的测试
            orderService.saveOrder(omsOrder);
            //重定向到支付系统
            return "redirect:http://payment.kmall.com:8088/index?outTradeNo="+outTradeNo+"&totalAmount="+totalAmount;
        }else{
            model.addAttribute("errMsg","获取用户订单错误");
            return "tradeFail";
        }
    }
}
