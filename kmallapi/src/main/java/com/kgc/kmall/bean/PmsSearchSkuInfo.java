package com.kgc.kmall.bean;

import java.io.Serializable;
import java.util.List;

public class PmsSearchSkuInfo implements Serializable {

    private Long id;
    private String skuName;
    private String skuDesc;
    private Long catalog3Id;
    private Double price;
    private String skuDefaultImg;
    private Double hotScore;
    private Long productId;
    private List<PmsSkuAttrValue> skuAttrValueList;

}
