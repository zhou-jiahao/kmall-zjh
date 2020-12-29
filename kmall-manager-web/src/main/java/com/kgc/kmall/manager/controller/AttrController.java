package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrValue;
import com.kgc.kmall.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "平台属性",description = "提供了平台属性表")
public class AttrController {

    @Reference
    AttrService attrService;

    @ApiOperation("根据三级分类id查询平台属性")
    @GetMapping("/attrInfoList")
    @ApiImplicitParam(name = "catalog3Id",value = "三级分类id")
    public List<PmsBaseAttrInfo> attrInfoList(Long catalog3Id){
        return attrService.select(catalog3Id);
    }

    @ApiOperation("添加平台属性和更改平台属性值")
    @PostMapping("/saveAttrInfo")
    public Integer saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo){
        Integer i = attrService.add(attrInfo);
        return i;
    }

    @ApiOperation("根据平台属性id查询平台属性值")
    @PostMapping("/getAttrValueList")
    @ApiImplicitParam(name = "attrId",value = "平台属性id")
    public List<PmsBaseAttrValue> getAttrValueList(Long attrId){
        return attrService.getAttrValueList(attrId);
    }

}
