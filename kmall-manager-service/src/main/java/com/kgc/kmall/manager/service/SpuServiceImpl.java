package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.*;
import com.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    PmsProductInfoMapper pmsProductInfoMapper;

    @Resource
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Resource
    PmsProductImageMapper pmsProductImageMapper;

    @Resource
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Resource
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        PmsProductInfoExample example=new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        return pmsProductInfoMapper.selectByExample(example);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> saleAttrList = pmsBaseSaleAttrMapper.selectByExample(null);
        return saleAttrList;
    }

    @Override
    public Integer saveSpuInfo(PmsProductInfo pmsProductInfo) {
        try {
            //添加Spu
            int insert = pmsProductInfoMapper.insert(pmsProductInfo);
            //添加图片
            List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
            if(spuImageList.size()!=0 && spuImageList!=null){
                for (PmsProductImage pmsProductImage : spuImageList) {
                    pmsProductImage.setProductId(pmsProductInfo.getId());
                    System.out.println("图片productid"+pmsProductInfo.getId());
                    pmsProductImageMapper.insert(pmsProductImage);
                }
            }
            //添加属性
            List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
            if(spuSaleAttrList!=null && spuSaleAttrList.size()!=0){
                for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
                    pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
                    //添加属性值
                    List<com.kgc.kmall.bean.PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
                    for (com.kgc.kmall.bean.PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                        pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                        pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
                    }
                    pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId) {
        PmsProductSaleAttrExample example=new PmsProductSaleAttrExample();
        PmsProductSaleAttrExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.selectByExample(example);
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrList) {
            PmsProductSaleAttrValueExample example1=new PmsProductSaleAttrValueExample();
            PmsProductSaleAttrValueExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andSaleAttrIdEqualTo(pmsProductSaleAttr.getSaleAttrId());
            criteria1.andProductIdEqualTo(spuId);

            List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList = pmsProductSaleAttrValueMapper.selectByExample(example1);
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValueList);
        }
        return pmsProductSaleAttrList;
    }

    @Override
    public List<PmsProductImage> spuImageList(Long spuId) {
        PmsProductImageExample imageExample=new PmsProductImageExample();
        imageExample.createCriteria().andProductIdEqualTo(spuId);
        return pmsProductImageMapper.selectByExample(imageExample);
    }
}
