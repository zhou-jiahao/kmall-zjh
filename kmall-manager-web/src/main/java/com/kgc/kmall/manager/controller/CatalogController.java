package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.bean.PmsBaseCatalog2;
import com.kgc.kmall.bean.PmsBaseCatalog3;
import com.kgc.kmall.service.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "三级分类",description = "提供了三级分类")
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @ApiOperation("一级分类")
    @PostMapping("/getCatalog1")
    public List<PmsBaseCatalog1> catalog1(){
        return catalogService.getCatalog1();
    }

    @ApiOperation("二级分类")
    @PostMapping("/getCatalog2")
    @ApiImplicitParam(name = "catalog1Id",value = "一级分类id")
    public List<PmsBaseCatalog2> catalog2(Integer catalog1Id){
        return catalogService.getCatalog2(catalog1Id);
    }

    @ApiOperation("三级分类")
    @PostMapping("/getCatalog3")
    @ApiImplicitParam(name = "catalog2Id",value = "二级分类id")
    public List<PmsBaseCatalog3> catalog3(Long catalog2Id){
        return catalogService.getCatalog3(catalog2Id);
    }


}
