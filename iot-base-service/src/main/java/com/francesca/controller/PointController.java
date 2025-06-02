package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.PointDao;
import com.francesca.dao.ProductDao;
import com.francesca.dao.SubsysDao;
import com.francesca.model.DTO.PointEntity;
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
@RequestMapping(UrlConstant.Point)
@Validated
@Api(tags = "Point")
public class PointController {

    @Autowired
    private PointDao pointDao;

    @Autowired
    private ProductDao productDao;

    @ApiOperation(value = "列出所有点位")
    @GetMapping( "getall")
    public List<PointEntity> getall() {

        return pointDao.selectAll();
    }

    @ApiOperation(value = "以产品查找点位")
    @GetMapping( "getByProd")
    public List<PointEntity> getByProd(@RequestParam int id) {

        return pointDao.selectByProduct(id);
    }

    @ApiOperation(value = "增加点位")
    @PostMapping( "add")
    public boolean add(@RequestBody PointEntity entity) {

        ProductEntity productEntity = productDao.selectByUid(entity.getProdid());
        if (ObjectUtil.isEmpty(productEntity)){
            return false;
        }

        pointDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改点位")
    @PostMapping( "update")
    public boolean update(@RequestBody PointEntity entity) {

        ProductEntity productEntity = productDao.selectByUid(entity.getProdid());
        if (ObjectUtil.isEmpty(productEntity)){
            return false;
        }

        pointDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除子系统")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return pointDao.delete(id);
    }


}
