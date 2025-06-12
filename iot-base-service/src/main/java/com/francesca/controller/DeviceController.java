package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.PointDao;
import com.francesca.dao.ProductDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.VO.Device.DevCmd;
import com.francesca.model.VO.Device.DevStatus;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.product.ProdPoint;
import com.francesca.service.CacheService;
import com.francesca.service.CommonService;
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
@RequestMapping(UrlConstant.Device)
@Validated
@Api(tags = "Device")
public class DeviceController {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PointDao pointDao;

    @Autowired
    private CommonService commonService;


    @ApiOperation(value = "列出指定设备当前点位值")
    @GetMapping( "getdevpoint")
    public DevStatus getDevPoint(@RequestParam int devId) {

        DeviceEntity device =  cacheService.getDevice(BigInteger.valueOf(devId));

        if(ObjectUtil.isEmpty(device)){
            return null;
        }

        List<PointEntity> pointEntities = pointDao.selectByProduct(device.getProduct());

        if(ObjectUtil.isEmpty(pointEntities)){
            return null;
        }

        List<ProdPoint> points = new ArrayList<>();
        for (PointEntity pointEntity : pointEntities){
            ProdPoint prodPoint = new ProdPoint();

            String pv = commonService.getPointValue(BigInteger.valueOf(devId), pointEntity.getId());

            prodPoint.setName(pointEntity.getName());
            prodPoint.setAlias(pointEntity.getAlias());
            prodPoint.setProdid(String.valueOf(pointEntity.getProdid()));
            prodPoint.setValue(pv);
            prodPoint.setUnit(pointEntity.getUnit());
            prodPoint.setId(String.valueOf(pointEntity.getId()));
            prodPoint.setPtUse(String.valueOf(pointEntity.getPtuse()));
            prodPoint.setType(String.valueOf(pointEntity.getType()));

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

        Device device1 = cacheService.get(BigInteger.valueOf(devId));

        DevStatus devStatus = new DevStatus();
        devStatus.setId(String.valueOf(devId));
        devStatus.setManu(device1.getManu());
        devStatus.setProdName(device1.getProduct());
        devStatus.setStatus(points);

        return devStatus;

    }

    @ApiOperation(value = "执行设备命令  成功返回OK")
    @PostMapping( "sndcmd")
    public String sndcmd(@RequestBody DevCmd devCmd) {



        if(ObjectUtil.isEmpty(devCmd.getDevId()) || ObjectUtil.isEmpty(devCmd.getId()) ){
            return  " id can not be empty ";
        }
        DeviceEntity device = cacheService.getDevice(new BigInteger(devCmd.getDevId()));

        if(ObjectUtil.isEmpty(device)){
            return "device not exist";
        }

        PointEntity pointEntity = cacheService.getPoint(new BigInteger(devCmd.getId()));

        if(ObjectUtil.isEmpty(pointEntity)){
            return "Point not exist";
        }

        if (pointEntity.getProdid() != device.getProduct()){
            return "Device can not support this cmd ";
        }

        if (pointEntity.getPtuse() < 2 ){
            return "Point is read only ";
        }

        Float pValue = Float.parseFloat(devCmd.getPValue());

        if (pValue > pointEntity.getHigh() || pValue < pointEntity.getLow()){
            return " parameters out of range ";
        }

        return "OK";

    }

    @ApiOperation(value = "列出所有设备")
    @GetMapping( "getall")
    public List<Device> getall() {

        return cacheService.getAllDevice();
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
