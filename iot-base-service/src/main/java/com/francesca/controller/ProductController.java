package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.ProductDao;
import com.francesca.dao.SubsysDao;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.SubsysEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author francesca

 * 2025-05-24
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Product)
@Validated
@Api(tags = "Product")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @ApiOperation(value = "列出所有产品")
    @GetMapping( "getall")
    public List<ProductEntity> getall() {

        return productDao.selectAll();
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
