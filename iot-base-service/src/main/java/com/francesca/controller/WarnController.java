package com.francesca.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.*;
import com.francesca.dao.WarnDao;
import com.francesca.model.DTO.*;
import com.francesca.model.DTO.WarnEntity;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.Warn.Warn;
import com.francesca.model.VO.dash.WarnRecordVO;
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

 * 2025-06-06
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Warn)
@Validated
@Api(tags = "Warn")
public class WarnController {

    @Autowired
    private WarnDao warnDao;

    @Autowired
    private WarnRecordDao warnRecordDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private SubsysDao subsysDao;


    @ApiOperation(value = "列出所有告警定义")
    @GetMapping( "getall")
    public List<WarnEntity> getalldto() {

        return warnDao.selectAll();
    }

    @ApiOperation(value = "列出当日所有告警")
    @GetMapping( "getTodayWarn")
    public List<WarnRecordVO> getTodayWarn() {

        List<WarnRecordEntity> warnRecordEntities =  warnRecordDao.selectAll();

        if(ObjectUtil.isEmpty(warnRecordEntities)){
            return null;
        }

        List<WarnRecordVO> warns = new ArrayList<>();

        for (WarnRecordEntity warnRecordEntity : warnRecordEntities){

            if (ObjectUtil.isNotEmpty(warnRecordEntity) &&  DateUtil.isSameDay(warnRecordEntity.getCreateTime(), DateUtil.date()) ){

                WarnRecordVO warnRecordVO = new WarnRecordVO();
                warnRecordVO.setId(warnRecordEntity.getId());

                if (ObjectUtil.isNotEmpty(warnRecordEntity.getArea())) {
                    AreaEntity areaEntity = areaDao.selectByUid(warnRecordEntity.getArea().intValue());
                    if (ObjectUtil.isNotEmpty(areaEntity)){
                        warnRecordVO.setArea(areaEntity.getAlias());
                    }

                }

                warnRecordVO.setCount(warnRecordEntity.getCount());
                warnRecordVO.setDelay(warnRecordEntity.getDelay());
                warnRecordVO.setDevid(warnRecordEntity.getDevid());
                warnRecordVO.setName(warnRecordEntity.getName());
                warnRecordVO.setAlias(warnRecordEntity.getAlias());
                warnRecordVO.setLevel(warnRecordEntity.getLevel());
                warnRecordVO.setRuleid(warnRecordEntity.getRuleid());
                warnRecordVO.setCreateTime(warnRecordEntity.getCreateTime());
                warnRecordVO.setCloseTime(warnRecordEntity.getCloseTime());
                warnRecordVO.setStatus(warnRecordEntity.getStatus());

                if (ObjectUtil.isNotEmpty(warnRecordEntity.getSubsys())){
                    SubsysEntity subsysEntity = subsysDao.selectByUid(warnRecordEntity.getSubsys().intValue());

                    if (ObjectUtil.isNotEmpty(subsysEntity)){
                        warnRecordVO.setSubsys(subsysEntity.getAlias());
                    }


                }

                Device device =  cacheService.get(warnRecordEntity.getDevid());
                warnRecordVO.setDevName(device.getAlias());
                warns.add(warnRecordVO);

            }



        }


        return warns;

    }

    @ApiOperation(value = "手工关闭告警")
    @PostMapping( "closeWarn")
    public boolean closeWarn(@RequestParam int id ) {

       WarnRecordEntity warnRecordEntity = warnRecordDao.selectByUid(BigInteger.valueOf(id));

       if (ObjectUtil.isEmpty(warnRecordEntity) || warnRecordEntity.getStatus() != 2 ){
           return false;
       }

       warnRecordEntity.setStatus(0);
       warnRecordEntity.setCloseTime(DateUtil.date());
        warnRecordDao.update(warnRecordEntity);

        return true;
    }


    @ApiOperation(value = "增加告警")
    @PostMapping( "add")
    public boolean add(@RequestBody WarnEntity entity) {
        warnDao.insert(entity);

        return true;
    }


    @ApiOperation(value = "修改告警")
    @PostMapping( "update")
    public boolean update(@RequestBody WarnEntity entity) {
        warnDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除告警")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return warnDao.delete(BigInteger.valueOf(id));
    }


}
