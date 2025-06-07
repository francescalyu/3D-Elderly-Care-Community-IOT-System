package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.WarnDao;
import com.francesca.dao.WarnDao;
import com.francesca.model.DTO.WarnEntity;
import com.francesca.model.DTO.WarnEntity;
import com.francesca.model.VO.Warn.Warn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
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


    @ApiOperation(value = "列出所有告警")
    @GetMapping( "getall")
    public List<WarnEntity> getalldto() {

        return warnDao.selectAll();
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
