package com.kgc.kmall.manager;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.bean.PmsSearchSkuInfo;
import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.service.CatalogService;
import com.kgc.kmall.service.SkuService;
import com.kgc.kmall.util.RedisUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class KmallManagerServiceApplicationTests {

//	@Resource
//	CatalogService catalogService;
//	@Resource
//    RedisUtil redisUtil;

    @Reference
    SkuService skuService;

    @Resource
    JestClient jestClient;

    @Test
    void contextLoads() {
        //查询全部的sku
        List<PmsSkuInfo> allSku = skuService.getAllSku();
        //声明一个搜索引擎的泛型集合
        List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
        //循环全部的sku
        for (PmsSkuInfo pmsSkuInfo : allSku) {
            //实例化自己创建的搜索引擎类
            PmsSearchSkuInfo pmsSearchSkuInfo=new PmsSearchSkuInfo();
            //把查询全部sku复制给搜索引擎
            BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
            //因为sku原来的productid改了叫spuId 所以要手动给它赋值
            pmsSearchSkuInfo.setProductId(pmsSkuInfo.getSpuId());
            //现在再把实例化的对象给泛型集合
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        // 导入es
        for (PmsSearchSkuInfo  pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index index=new Index.Builder(pmsSearchSkuInfo).index("kmall").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
            try {
                jestClient.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//	@Test
//	void contextLoads() {
//        List<PmsBaseCatalog1> catalog1 = catalogService.getCatalog1();
//        for (PmsBaseCatalog1 pmsBaseCatalog1 : catalog1) {
//            System.out.println(catalog1.toString());
//        }
//    }
//
//    @Test
//    void test01(){
//        Jedis jedis = redisUtil.getJedis();
//        String ping = jedis.ping();
//        System.out.println(ping);
//    }

}
