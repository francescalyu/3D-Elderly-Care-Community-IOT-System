package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.Warn.WarnCondition;
import com.francesca.model.VO.Warn.WarnRule;
import com.francesca.model.VO.Warn.WarnRuleItem;
import com.francesca.model.VO.Warn.WarnRuleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


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
    private PointDao pointDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private WarnDao warnDao;


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
                warnRule1.setProd(productEntity.getId().intValue());
                warnRule1.setProdName(productEntity.getAlias());
            }


            warnRule1.setName(warnRule.getName());
            warnRule1.setAlias(warnRule.getAlias());
            warnRule1.setPoint(warnRule.getPid().intValue());
            warnRule1.setOp(warnRule.getOp());
            warnRule1.setOpValue(warnRule.getPvalue());
            warnRule1.setTime(warnRule.getTime());
            warnRule1.setCount(warnRule.getCount());
            warnRule1.setConn(warnRule.getConn());
            warnRule1.setType(warnRule.getType());

            if(ObjectUtil.isNotEmpty(warnRule.getDev()));
            DeviceEntity deviceEntity = deviceDao.selectByUid(BigInteger.valueOf(warnRule.getDev()));

            if(ObjectUtil.isNotEmpty(deviceEntity)) {
                warnRule1.setDev(deviceEntity.getId().intValue());
                warnRule1.setDevName(deviceEntity.getAlias());
            }

            if (ObjectUtil.isNotEmpty(warnRule.getArea())){
                AreaEntity areaEntity = areaDao.selectByUid(warnRule.getArea());
                if(ObjectUtil.isNotEmpty(areaEntity)){
                    warnRule1.setArea(areaEntity.getId().intValue());
                    warnRule1.setAreaName(areaEntity.getAlias());
                }
            }

            if (ObjectUtil.isNotEmpty(warnRule.getSubsys())){
                warnRule1.setSubsys(warnRule.getSubsys());
            }

            if (ObjectUtil.isNotEmpty(warnRule.getWarnid())) {
                WarnEntity warn = warnDao.selectByUid(BigInteger.valueOf(warnRule.getWarnid()));

                if (ObjectUtil.isNotEmpty(warn)){
                    warnRule1.setLevel(warn.getLevel());
                }
            }

            out.add(warnRule1);
           }

        return out;

    }


    @ApiOperation(value = "以ruleId 查询指定规则")
    @GetMapping( "getRule")
    public WarnRuleVO getRule(@RequestParam int ruleId) {

        if (ObjectUtil.isEmpty(ruleId)){
            return null;
        }

        List<WarnRuleEntity> finds = warnRuleDao.selectByRuleId(BigInteger.valueOf(ruleId));

        WarnRuleVO res = new WarnRuleVO();

        if(ObjectUtil.isEmpty(finds)){
            return null;
        }

        res.setId(finds.get(0).getId().intValue());
        res.setRuleid(finds.get(0).getRuleid().intValue());
        res.setName(finds.get(0).getName());
        res.setAlias(finds.get(0).getAlias());
        res.setArea(finds.get(0).getArea());
        res.setSubsys(finds.get(0).getSubsys());
        res.setCount(finds.get(0).getCount());
        res.setTime(finds.get(0).getTime());
        res.setType(finds.get(0).getType());
        res.setIsEnable(finds.get(0).getIsenable());

        WarnEntity warnEntity = warnDao.selectByUid(BigInteger.valueOf(finds.get(0).getWarnid()));
        if(ObjectUtil.isNotEmpty(warnEntity)) {
            res.setLevel(warnEntity.getLevel());
        }


        List<WarnRuleItem> items = new ArrayList<>();

        for (WarnRuleEntity warnRule : finds){

            WarnRuleItem warnRule1 = new WarnRuleItem();
            if (ObjectUtil.isNotEmpty(warnRule.getProd())) {
                warnRule1.setProd(warnRule.getProd());
            }
            warnRule1.setId(warnRule.getId().intValue());
            warnRule1.setPoint(warnRule.getPid().intValue());
            warnRule1.setOp(warnRule.getOp());
            warnRule1.setOpValue(warnRule.getPvalue());
            warnRule1.setConn(warnRule.getConn());

            if (ObjectUtil.isNotEmpty(warnRule.getDev())) {
                warnRule1.setDev(warnRule.getDev());
            }

            warnRule1.setCloseWarn(warnRule.getClosewarn());
            items.add(warnRule1);

        }

        res.setItemList(items);

        return res;

    }


    @ApiOperation(value = "列出指定规则的附加条件")
    @GetMapping( "getCondByRuld")
    public List<WarnCondition> getCondbyRule(@RequestParam int ruleId){

           List<WarnRuleEntity> rules = warnRuleDao.selectByRuleId(BigInteger.valueOf(ruleId));

           if (ObjectUtil.isEmpty(rules)){
               return null;
           }
           List<WarnCondition> res = new ArrayList<>();

           for (WarnRuleEntity rule : rules){
               WarnCondition condition = new WarnCondition();
               condition.setRuleid( rule.getRuleid().intValue() );
               condition.setCloseWarn(rule.getClosewarn());
               condition.setConn(rule.getConn());

               condition.setDev(rule.getDev());

               condition.setOp(rule.getOp());
               condition.setPoint(rule.getPoint());
               condition.setOpValue(rule.getPvalue());
               condition.setProd(rule.getProd());

               res.add(condition);
           }
           return res;

    }



    @ApiOperation(value = "列出所有规则DTO")
    @GetMapping( "getalldto")
    public List<WarnRuleEntity> getalldto() {

        return warnRuleDao.selectAll();
    }

    @ApiOperation(value = "增加规则")
    @PostMapping( "add")
    public String add(@RequestBody WarnRuleVO warnRule) {

        String res = checkWarnVO(warnRule);

        //参数校验通过
        if (res.length() <=1 ) {

            BigInteger ruleId = new BigInteger("0");
            if (warnRule.getRuleid() == 0) {
                List<WarnRuleEntity> rulefinds = warnRuleDao.selectAll();
                Optional<BigInteger> maxId = rulefinds.stream()
                        .map(WarnRuleEntity::getId)
                        .max(Comparator.naturalOrder());

               ruleId  = maxId.orElse(new BigInteger("-1")); // 如果列表为空则返回0

            }






            List<WarnRuleItem> items = warnRule.getItemList();

            for (WarnRuleItem item : items) {

                WarnRuleEntity warnRuleEntity = new WarnRuleEntity();

                warnRuleEntity.setTime(warnRule.getTime());
                warnRuleEntity.setId(BigInteger.valueOf(0));
                warnRuleEntity.setIsenable(warnRule.getIsEnable());
                warnRuleEntity.setType(warnRule.getType());
                warnRuleEntity.setCount(warnRule.getCount());
                warnRuleEntity.setName(warnRule.getName());
                warnRuleEntity.setAlias(warnRule.getAlias());
                warnRuleEntity.setWarnid(warnRule.getWarnId());
                warnRuleEntity.setRuleid(ruleId);
                if (ObjectUtil.isNotEmpty(warnRule.getArea())) {
                    warnRuleEntity.setArea(warnRule.getArea());
                }
                if (ObjectUtil.isNotEmpty(warnRule.getSubsys())) {
                    warnRuleEntity.setSubsys(warnRule.getSubsys());
                }

                warnRuleEntity.setClosewarn(item.getCloseWarn());
                if (ObjectUtil.isNotEmpty(item.getDev())) {
                    warnRuleEntity.setDev(item.getDev());
                }
                warnRuleEntity.setConn(item.getConn());
                warnRuleEntity.setOp(item.getOp());

                if (ObjectUtil.isNotEmpty(item.getProd())){
                    warnRuleEntity.setProd(item.getProd());
                }


                if (ObjectUtil.isNotEmpty(item.getPoint())){

                    warnRuleEntity.setPid(BigInteger.valueOf(item.getPoint()));
                    PointEntity pointEntity = pointDao.selectByUid(item.getPoint());
                    if (ObjectUtil.isNotEmpty(pointEntity)){
                        warnRuleEntity.setPoint(pointEntity.getName());
                    }


                }

                warnRuleEntity.setPvalue(item.getOpValue());

                warnRuleDao.insert(warnRuleEntity);

            }

            res = "已增加 " + items.size() + "条规则";
        }

        return res;

    }

    @ApiOperation(value = "修改规则")
    @PostMapping( "upd")
    public String upd(@RequestBody WarnRuleVO warnRule) {

        String res = checkWarnVO(warnRule);

        //参数校验通过
        if (res.length() <=1 ) {
            List<WarnRuleEntity> finds = warnRuleDao.selectByRuleId(BigInteger.valueOf(warnRule.getRuleid()));

            WarnEntity findWarns = warnDao.selectByUid(BigInteger.valueOf(warnRule.getWarnId()));
            if (ObjectUtil.isNotEmpty(findWarns)){

                findWarns.setName(warnRule.getName());
                findWarns.setAlias(warnRule.getAlias());
                findWarns.setLevel(warnRule.getLevel());

                if (ObjectUtil.isNotEmpty(warnRule.getSubsys())) {
                    findWarns.setSubsys(BigInteger.valueOf(warnRule.getSubsys()));
                }

                if (ObjectUtil.isNotEmpty(warnRule.getArea())){
                    findWarns.setArea(BigInteger.valueOf(warnRule.getArea()));
                }

                warnDao.update(findWarns);

            }



            List<WarnRuleItem> items = warnRule.getItemList();

            for (WarnRuleItem item : items) {

                WarnRuleEntity warnRuleEntity = new WarnRuleEntity();



                warnRuleEntity.setTime(warnRule.getTime());
                warnRuleEntity.setId(BigInteger.valueOf(0));
                warnRuleEntity.setIsenable(warnRule.getIsEnable());
                warnRuleEntity.setType(warnRule.getType());
                warnRuleEntity.setCount(warnRule.getCount());
                warnRuleEntity.setName(warnRule.getName());
                warnRuleEntity.setAlias(warnRule.getAlias());
                warnRuleEntity.setWarnid(warnRule.getWarnId());
                warnRuleEntity.setRuleid(BigInteger.valueOf(warnRule.getRuleid()));

                if (ObjectUtil.isEmpty(item.getId())){
                    return "规则项id不可为空";
                }
                warnRuleEntity.setId(BigInteger.valueOf(item.getId()));

                if (ObjectUtil.isNotEmpty(warnRule.getArea())) {
                    warnRuleEntity.setArea(warnRule.getArea());
                }
                if (ObjectUtil.isNotEmpty(warnRule.getSubsys())) {
                    warnRuleEntity.setSubsys(warnRule.getSubsys());
                }

                warnRuleEntity.setClosewarn(item.getCloseWarn());
                if (ObjectUtil.isNotEmpty(item.getDev())) {
                    warnRuleEntity.setDev(item.getDev());
                }
                warnRuleEntity.setConn(item.getConn());
                warnRuleEntity.setOp(item.getOp());

                if (ObjectUtil.isNotEmpty(item.getProd())){
                    warnRuleEntity.setProd(item.getProd());
                }


                if (ObjectUtil.isNotEmpty(item.getPoint())){

                    warnRuleEntity.setPid(BigInteger.valueOf(item.getPoint()));
                    PointEntity pointEntity = pointDao.selectByUid(item.getPoint());
                    if (ObjectUtil.isNotEmpty(pointEntity)){
                       warnRuleEntity.setPoint(pointEntity.getName());
                    }


                }

                warnRuleDao.update(warnRuleEntity);

            }

            res = "已更新 " + items.size() + "条规则";
        }

        return res;

    }

    @ApiOperation(value = "删除规则")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int ruleid) {

        List<WarnRuleEntity> finds = warnRuleDao.selectByRuleId(BigInteger.valueOf(ruleid));

        if (ObjectUtil.isEmpty(finds)){
            return false;
        }

        for (WarnRuleEntity warnRuleEntity : finds){
            warnRuleDao.delete(warnRuleEntity.getId());
        }

        return true;
    }


    private String checkWarnVO(WarnRuleVO warnRule){

        if (ObjectUtil.isEmpty(warnRule.getRuleid())){
            return "规则ID不可为空";
        }

        if (ObjectUtil.isEmpty(warnRule.getAlias())){
            return "规则名不可为空";
        }

        List<WarnRuleItem> items = warnRule.getItemList();

        if (ObjectUtil.isEmpty(items)){
            return  "规则不可为空";

        }

        for (WarnRuleItem item : items) {

            if (ObjectUtil.isEmpty(item.getProd() )  && ObjectUtil.isEmpty(item.getDev()) ) {
                return "规则关联产品或不可为空";
            }

            if (ObjectUtil.isEmpty(item.getPoint()) || ObjectUtil.isEmpty(item.getOpValue()) || ObjectUtil.isEmpty(item.getOp())) {
                return " 告警表达式非法 ";
            }

            if (ObjectUtil.isEmpty(warnRule.getType()) || warnRule.getType() > 2) {
                return "告警类型非法";
            }
        }

         return "";


    }

}
