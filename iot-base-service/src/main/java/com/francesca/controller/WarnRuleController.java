package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.Warn.WarnRule;
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
@RequestMapping(UrlConstant.WarnRule)
@Validated
@Api(tags = "告警规则")
public class WarnRuleController {

    @Autowired
    private WarnRuleDao warnRuleDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SubsysDao subsysDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private DeviceDao deviceDao;


    @ApiOperation(value = "列出所有规则")
    @GetMapping( "getall")
    public List<WarnRule> getall() {
        List<WarnRuleEntity> warnRuleEntities = warnRuleDao.selectAll();

        List<WarnRule> out = new ArrayList<>();

        if(ObjectUtil.isEmpty(warnRuleEntities)){
            return out;
        }

        for (WarnRuleEntity warnRule : warnRuleEntities){

            WarnRule warnRule1 = new WarnRule();
            warnRule1.setId(String.valueOf(warnRule.getId()));
            warnRule1.setRuleid(warnRule.getRuleid().intValue());

            ProductEntity productEntity = productDao.selectByUid(warnRule.getProd());
            if(ObjectUtil.isNotEmpty(productEntity)){
                warnRule1.setProd(productEntity.getAlias());
            }

            warnRule1.setName(warnRule.getName());
            warnRule1.setAlias(warnRule.getAlias());
            warnRule1.setPoint(warnRule.getPoint());
            warnRule1.setOp(warnRule.getOp());
            warnRule1.setOpValue(warnRule.getPvalue());
            warnRule1.setTime(warnRule.getTime());
            warnRule1.setCount(warnRule.getCount());
            warnRule1.setConn(warnRule.getConn());
            warnRule1.setType(warnRule.getType());

            if(ObjectUtil.isNotEmpty(warnRule.getDev()));
            DeviceEntity deviceEntity = deviceDao.selectByUid(BigInteger.valueOf(warnRule.getDev()));

            if(ObjectUtil.isNotEmpty(deviceEntity)) {
                warnRule1.setDev(deviceEntity.getAlias());
            }

            if (ObjectUtil.isNotEmpty(warnRule.getArea())){
                AreaEntity areaEntity = areaDao.selectByUid(warnRule.getArea());
                if(ObjectUtil.isNotEmpty(areaEntity)){
                    warnRule1.setArea(areaEntity.getAlias());
                }
            }


            out.add(warnRule1);
           }

        return out;

    }

    @ApiOperation(value = "列出所有规则DTO")
    @GetMapping( "getalldto")
    public List<WarnRuleEntity> getalldto() {

        return warnRuleDao.selectAll();
    }

    @ApiOperation(value = "增加规则")
    @PostMapping( "add")
    public String add(@RequestBody WarnRule warnRule) {

        String res = checkWarnVO(warnRule);

        if (res.length() <=1 ){
            WarnRuleEntity warnRuleEntity = new WarnRuleEntity();

            warnRuleEntity.setClosewarn(Integer.valueOf(warnRule.getCloseWarn()));

            if (ObjectUtil.isNotEmpty(warnRule.getDev())) {
                warnRuleEntity.setDev(Integer.parseInt(warnRule.getDev()));
            }
            warnRuleEntity.setConn(warnRule.getConn());

            if (ObjectUtil.isNotEmpty(warnRule.getArea())) {
                warnRuleEntity.setArea(Integer.parseInt(warnRule.getArea()));
            }


            warnRuleEntity.setCount(warnRule.getCount());
            warnRuleEntity.setName(warnRule.getName());
            warnRuleEntity.setAlias(warnRule.getAlias());
            warnRuleEntity.setOp(warnRule.getOp());
            warnRuleEntity.setPid(new BigInteger(warnRule.getPoint()));
            warnRuleEntity.setType(warnRule.getType());

            if (ObjectUtil.isNotEmpty(warnRule.getSubsys())) {
                warnRuleEntity.setSubsys(Integer.parseInt(warnRule.getSubsys()));
            }

           warnRuleEntity.setTime(warnRule.getTime());
           warnRuleDao.insert(warnRuleEntity);

        }

        return res;

    }

    @ApiOperation(value = "修改规则")
    @PostMapping( "update")
    public String update(@RequestBody WarnRule warnRule) {
        String res = checkWarnVO(warnRule);

        if (res.length() <=1 ){
            WarnRuleEntity warnRuleEntity = new WarnRuleEntity();

            warnRuleEntity.setClosewarn(Integer.valueOf(warnRule.getCloseWarn()));

            if (ObjectUtil.isNotEmpty(warnRule.getDev())) {
                warnRuleEntity.setDev(Integer.parseInt(warnRule.getDev()));
            }
            warnRuleEntity.setConn(warnRule.getConn());

            if (ObjectUtil.isNotEmpty(warnRule.getArea())) {
                warnRuleEntity.setArea(Integer.parseInt(warnRule.getArea()));
            }


            warnRuleEntity.setCount(warnRule.getCount());
            warnRuleEntity.setName(warnRule.getName());
            warnRuleEntity.setAlias(warnRule.getAlias());
            warnRuleEntity.setOp(warnRule.getOp());
            warnRuleEntity.setPid(new BigInteger(warnRule.getPoint()));
            warnRuleEntity.setType(warnRule.getType());

            if (ObjectUtil.isNotEmpty(warnRule.getSubsys())) {
                warnRuleEntity.setSubsys(Integer.parseInt(warnRule.getSubsys()));
            }

            warnRuleEntity.setTime(warnRule.getTime());
            warnRuleDao.update(warnRuleEntity);

        }

        return res;
    }

    @ApiOperation(value = "删除规则")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return warnRuleDao.delete(BigInteger.valueOf(id));
    }


    private String checkWarnVO(WarnRule warnRule){

        if (ObjectUtil.isEmpty(warnRule.getRuleid())){
            return "规则ID不可为空";
        }

        if (ObjectUtil.isEmpty(warnRule.getAlias())){
            return "规则名不可为空";
        }

        if (ObjectUtil.isEmpty(warnRule.getProd())){
            return "规则关联产品不可为空";
        }

        if (ObjectUtil.isEmpty(warnRule.getPoint()) || ObjectUtil.isEmpty(warnRule.getOpValue()) || ObjectUtil.isEmpty(warnRule.getOp())){
            return " 告警表达式非法 ";
        }

        if (ObjectUtil.isEmpty(warnRule.getType()) || warnRule.getType() > 2){
            return "告警类型非法";
        }

         return "";


    }

}
