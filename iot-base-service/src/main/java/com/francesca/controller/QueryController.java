package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.Health1hDao;
import com.francesca.dao.Power5minDao;
import com.francesca.model.DTO.Health1hEntity;
import com.francesca.model.DTO.Power5minEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @Author francesca

 * 2025-05-24
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Query)
@Validated
@Api(tags = "查询数据")
public class QueryController {

    @Autowired
    private Power5minDao power5minDao;
    @Autowired
    private Health1hDao health1hDao;

    @ApiOperation(value = "按日期查询能源数据 YYYYMMDD")
    @PostMapping( "getPowerByDate")
    public List<Power5minEntity> getPowerByDate(@RequestParam String qdate) {

        if(ObjectUtil.isEmpty(qdate) || qdate.length() < 8 ){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(qdate, formatter);

        return power5minDao.selectbydate(localDate , 1);
    }

    @ApiOperation(value = "按日期查询健康数据 YYYYMMDD")
    @PostMapping( "getHealthByDate")
    public List<Health1hEntity> getHealthByDate(@RequestParam String qdate) {
        if(ObjectUtil.isEmpty(qdate) || qdate.length() < 8 ){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(qdate, formatter);

        return health1hDao.selectbydate(localDate);
    }

    }
