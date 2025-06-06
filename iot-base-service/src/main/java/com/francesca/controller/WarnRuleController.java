package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.WarnRuleDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.WarnRuleEntity;
import com.francesca.model.VO.Warn.WarnRule;
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
@RequestMapping(UrlConstant.WarnRule)
@Validated
@Api(tags = "WarnRule")
public class WarnRuleController {

    @Autowired
    private WarnRuleDao warnRuleDao;

    @ApiOperation(value = "列出所有规则")
    @GetMapping( "getall")
    public List<WarnRule> getall() {

        return null;
    }

    @ApiOperation(value = "列出所有规则DTO")
    @GetMapping( "getalldto")
    public List<WarnRuleEntity> getalldto() {

        return warnRuleDao.selectAll();
    }

    @ApiOperation(value = "增加规则")
    @PostMapping( "add")
    public boolean add(@RequestBody WarnRuleEntity entity) {
        warnRuleDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改规则")
    @PostMapping( "update")
    public boolean update(@RequestBody WarnRuleEntity entity) {
        warnRuleDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除规则")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return warnRuleDao.delete(BigInteger.valueOf(id));
    }


}
