package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.PointDao;
import com.francesca.dao.ProductDao;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.VO.product.ProdPoint;
import com.francesca.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author francesca

 * 2025-05-24
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Product)
@Validated
@Api(tags = "产品")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PointDao pointDao;

    @Autowired
    private CacheService cacheService;


    @ApiOperation(value = "列出产品物模型")
    @GetMapping( "getPoint")
    public List<ProdPoint> getPoint(@RequestParam int id) {
        List<PointEntity> pointEntities = pointDao.selectByProduct(id);

        if(ObjectUtil.isEmpty(pointEntities)){
            return null;
        }

        List<ProdPoint> points = new ArrayList<>();
        for (PointEntity pointEntity : pointEntities){
            ProdPoint prodPoint = new ProdPoint();

            prodPoint.setName(pointEntity.getName());
            prodPoint.setAlias(pointEntity.getAlias());
            prodPoint.setProdid(String.valueOf(pointEntity.getProdid()));
            prodPoint.setUnit(pointEntity.getUnit());
            prodPoint.setId(String.valueOf(pointEntity.getId()));
            prodPoint.setType(String.valueOf(pointEntity.getType()));
            prodPoint.setPtUse(String.valueOf(pointEntity.getPtuse()));

            if(ObjectUtil.isNotEmpty(pointEntity.getCmd())) {
                prodPoint.setCmd(pointEntity.getCmd());
            }
            if(ObjectUtil.isNotEmpty(pointEntity.getLow())) {
                prodPoint.setLow(String.valueOf(pointEntity.getLow()));
            }

            if(ObjectUtil.isNotEmpty(pointEntity.getHigh())) {
                prodPoint.setHigh(String.valueOf(pointEntity.getHigh()));
            }

            if(ObjectUtil.isNotEmpty(pointEntity.getStep())){
                prodPoint.setStep(String.valueOf(pointEntity.getStep()));
            }

            points.add(prodPoint);
        }

        return points;

    }


    @ApiOperation(value = "列出所有产品")
    @GetMapping( "getall")
    public List<ProductEntity> getall() {

        return productDao.selectAll();
    }

    @ApiOperation(value = "列出指定产品")
    @GetMapping( "getProd")
    public ProductEntity getProd(@RequestParam int id) {

        return   cacheService.getProduct(BigInteger.valueOf(id));
    }

    @ApiOperation(value = "按子系统查询产品")
    @GetMapping( "getProdBySubsys")
    public List<ProductEntity> getProdBySubsys(int subsysId) {

        if(ObjectUtil.isEmpty(subsysId)){
            return null;
        }

        return productDao.selectBySubSys(subsysId);
    }

    @ApiOperation(value = "增加产品")
    @PostMapping( "add")
    public boolean add(@RequestBody ProductEntity entity) {
        productDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改产品")
    @PostMapping( "update")
    public boolean update(@RequestBody ProductEntity entity) {
        productDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除产品")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {
        return productDao.delete(id);
    }

}
