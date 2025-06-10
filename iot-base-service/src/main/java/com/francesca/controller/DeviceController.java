package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.ProductDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.ProductEntity;
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
@RequestMapping(UrlConstant.Device)
@Validated
@Api(tags = "Device")
public class DeviceController {

    @Autowired
    private DeviceDao deviceDao;


    @ApiOperation(value = "列出指定设备当前点位值")
    @GetMapping( "getdevpoint")
    public List<DeviceEntity> getDevPoint() {

        return deviceDao.selectAll();
    }

    @ApiOperation(value = "列出所有设备")
    @GetMapping( "getall")
    public List<DeviceEntity> getall() {

        return deviceDao.selectAll();
    }

    @ApiOperation(value = "增加设备")
    @PostMapping( "add")
    public boolean add(@RequestBody DeviceEntity entity) {
        deviceDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改设备")
    @PostMapping( "update")
    public boolean update(@RequestBody DeviceEntity entity) {
        deviceDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除设备")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return deviceDao.delete(id);
    }


}
