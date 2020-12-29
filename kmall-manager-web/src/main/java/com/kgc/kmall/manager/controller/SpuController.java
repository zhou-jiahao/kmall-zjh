package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "商品Spu",description = "提供了商品Spu")
public class SpuController {

    @Reference
    SpuService spuService;


    @ApiOperation("根据三级分类id查询商品")
    @GetMapping("/spuList")
    @ApiImplicitParam(name = "catalog3Id",value = "三级分类id")
    public List<PmsProductInfo> pmsProductInfo(Long catalog3Id){
        return spuService.spuList(catalog3Id);
    }


    @ApiOperation("图片上传")
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file")MultipartFile file){
        try {
            //文件上传
            //返回文件上传后的路径
            String comFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init("D:\\第三期\\IdeaProjects\\kmall-zjh\\kmall-manager-web\\target\\classes\\tracker.conf");
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            String[] upload_file = storageClient.upload_file(file.getBytes(), extension, null);
            String path="http://192.168.56.138";
            for (int i = 0; i < upload_file.length; i++) {
                String s = upload_file[i];
                System.out.println("s = " + s);
                path+="/"+s;
            }
            System.out.println(path);
            return path;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    @ApiOperation("销售属性")
    @PostMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> saleAttrList = spuService.baseSaleAttrList();
        return saleAttrList;
    }

    @ApiOperation("添加商品Spu")
    @PostMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        //添加spu
        //保存数据库
        Integer integer = spuService.saveSpuInfo(pmsProductInfo);
        return integer>0?"success":"fail";
    }


    @ApiOperation("根据spuId查询销售属性")
    @GetMapping("/spuSaleAttrList")
    @ApiImplicitParam(name = "spuId",value = "商品Id")
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId){
        List<PmsProductSaleAttr> pmsProductSaleAttrList=spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrList;
    }

    @ApiOperation("查看spuId查询指定图片")
    @GetMapping("/spuImageList")
    @ApiImplicitParam(name = "spuId",value = "商品Id")
    public List<PmsProductImage> spuImageList(Long spuId){
        List<PmsProductImage> pmsProductImageList = spuService.spuImageList(spuId);
        return pmsProductImageList;
    }
}
