package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import com.francesca.service.DeviceCmd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author francesca

 * 2025-05-24
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Device)
@Validated
@Api(tags = "设备")
public class DeviceController {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PointDao pointDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DeviceCmd deviceCmd;


    @ApiOperation(value = "按子系统查询设备")
    @GetMapping( "getDevBySubsys")
    public List<Device> getDevBySubsys(int subsysId) {

        if(ObjectUtil.isEmpty(subsysId)){
            return null;
        }

         List<Device> res = cacheService.getAllDevice();

         return  res.stream().filter(v -> Integer.parseInt(v.getSysId() )== subsysId).collect(Collectors.toList());

    }


    @ApiOperation(value = "查询设备指定点位值")
    @GetMapping( "getdevonepoint")
    public ProdPoint getDevOnePoint(@RequestParam int devId , int pointId) {

        DeviceEntity device =  cacheService.getDevice(BigInteger.valueOf(devId));

        if(ObjectUtil.isEmpty(device) || ObjectUtil.isEmpty(pointId)){
            return null;
        }

        List<PointEntity> pointEntities = pointDao.selectByProduct(device.getProduct());

        if(ObjectUtil.isEmpty(pointEntities)){
            return null;
        }

        PointEntity pointEntity = pointEntities.stream().filter(v->v.getId().intValue() == pointId).findFirst().orElse(null);



        if (ObjectUtil.isEmpty(pointEntity)){
            return null;
        }

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

        return prodPoint;

    }


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

    @ApiOperation(value = "以孪生uuid查询设备当前点位值")
    @GetMapping( "gettwinpoint")
    public DevStatus getTwinPoint(@RequestParam String uuid) {

        if (ObjectUtil.isEmpty(uuid) || uuid.length() < 5){
            return  null;
        }

       List<Device> alldev =  cacheService.getAllDevice();

       Device find = null;

       for (Device device : alldev){

           String tuid = device.getTwinuid();
           if(ObjectUtil.isNotEmpty(tuid)){

               if (tuid.trim().equalsIgnoreCase(uuid.trim())){
                   find = device;
                   break;
               }
           }
       }


        if(ObjectUtil.isEmpty(find)){
            return null;
        }

        List<PointEntity> pointEntities = pointDao.selectByProduct(find.getPid().intValue());

        if(ObjectUtil.isEmpty(pointEntities)){
            return null;
        }

        List<ProdPoint> points = new ArrayList<>();
        for (PointEntity pointEntity : pointEntities){
            ProdPoint prodPoint = new ProdPoint();

            String pv = commonService.getPointValue(find.getId(), pointEntity.getId());

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

        DevStatus devStatus = new DevStatus();
        devStatus.setId(String.valueOf(find.getId()));
        devStatus.setManu(find.getManu());
        devStatus.setProdName(find.getProduct());
        devStatus.setStatus(points);

        return devStatus;

    }

    @ApiOperation(value = "执行设备命令  成功返回OK")
    @PostMapping( "sndcmd")
    public String sndcmd(@RequestBody DevCmd devCmd) {



        if(ObjectUtil.isEmpty(devCmd.getDevId()) || ObjectUtil.isEmpty(devCmd.getId()) ){
            return  " 设备ID不可为空 ";
        }

        return deviceCmd.sndCmd(devCmd);
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
