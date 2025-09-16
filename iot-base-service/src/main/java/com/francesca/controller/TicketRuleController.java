package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.PeopleDao;
import com.francesca.dao.TkRuleDao;
import com.francesca.dao.WarnRuleDao;
import com.francesca.model.DTO.PeopleEntity;
import com.francesca.model.DTO.TkruleEntity;
import com.francesca.model.DTO.WarnRuleEntity;
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

 * 2025-07-31
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.TicketRule)
@Validated
@Api(tags = "工单策略")
public class TicketRuleController {

    @Autowired
    private TkRuleDao tkRuleDao;


    @Autowired
    private WarnRuleDao warnRuleDao;

    @Autowired
    private PeopleDao peopleDao;


    @ApiOperation(value = "列出所有工单策略")
    @GetMapping( "getPolicyall")
    public List<TkruleEntity> getPolicyall() {

        return tkRuleDao.selectAll();
    }

    @ApiOperation(value = "以告警策略号查询关联工单策略 ")
    @GetMapping( "getPolicyByWarn")
    public List<TkruleEntity> getMonthWarn(@RequestParam Integer warnPolicyId) {

       WarnRuleEntity rule = warnRuleDao.selectByUid(BigInteger.valueOf(warnPolicyId));

       if (ObjectUtil.isEmpty(rule)){
           return null;
       }

       List<TkruleEntity> res = tkRuleDao.selectByWarnId(BigInteger.valueOf(rule.getWarnid()));

       return  res;

    }


    @ApiOperation(value = " 新增工单策略 ")
    @PostMapping( "addPolicy")
    public String addPolicy(@RequestBody TkruleEntity tkruleEntity) {

         if (ObjectUtil.isEmpty(tkruleEntity) || ObjectUtil.isEmpty(tkruleEntity.getWarnid())  ){
             return "工单策略关联告警策略号不可为空";
         }

         WarnRuleEntity warnRule = warnRuleDao.selectByUid(tkruleEntity.getWarnid());

         if (ObjectUtil.isEmpty(warnRule)){
             return "无关联的告警策略";
         }

         if (ObjectUtil.isEmpty(tkruleEntity.getName() )  || ObjectUtil.isEmpty(tkruleEntity.getAlias()) ){
             return "策略名不可为空";
         }

         if (ObjectUtil.isNotEmpty(tkruleEntity) && tkruleEntity.getTktype() == 2 ){
            List<TkruleEntity> tkrules = tkRuleDao.selectByWarnId(BigInteger.valueOf(warnRule.getWarnid()));

            if (ObjectUtil.isNotEmpty(tkrules)){
                return  "人工工单策略已存在，不可重复添加";
            }

            PeopleEntity people = peopleDao.selectByUid(tkruleEntity.getPeopleid());

            if (ObjectUtil.isEmpty(people) || people.getId() < 2){
                return "无此维护人员";
            }

            if (ObjectUtil.isEmpty(tkruleEntity.getExpiretime()) ||  tkruleEntity.getExpiretime() < 5  || tkruleEntity.getExpiretime() > 240 ){
                return "人工工单过期时间应在5分钟-6小时之间";
            }

         }

         tkruleEntity.setWarnid(BigInteger.valueOf(warnRule.getWarnid()));

         tkRuleDao.insert(tkruleEntity);
          return "新增成功";

    }



    @ApiOperation(value = " 修改工单策略 ")
    @PostMapping( "updPolicy")
    public String updPolicy(@RequestBody TkruleEntity tkruleEntity) {

        if (ObjectUtil.isEmpty(tkruleEntity) || ObjectUtil.isEmpty(tkruleEntity.getWarnid())  ){
            return "工单策略关联告警策略号不可为空";
        }

        TkruleEntity find = tkRuleDao.selectByUid(tkruleEntity.getId());

        if (ObjectUtil.isEmpty(find)){
            return "无此工单策略";
        }

        WarnRuleEntity warnRule = warnRuleDao.selectByUid(tkruleEntity.getWarnid());

        if (ObjectUtil.isEmpty(warnRule)){
            return "无关联的告警策略";
        }

        if (ObjectUtil.isEmpty(tkruleEntity.getName() )  || ObjectUtil.isEmpty(tkruleEntity.getAlias()) ){
            return "策略名不可为空";
        }

        if (ObjectUtil.isNotEmpty(tkruleEntity) && tkruleEntity.getTktype() == 2 ){
            List<TkruleEntity> tkrules = tkRuleDao.selectByWarnId(BigInteger.valueOf(warnRule.getWarnid()));

            if (ObjectUtil.isNotEmpty(tkrules)){
                return  "人工工单策略已存在，不可重复添加";
            }

            PeopleEntity people = peopleDao.selectByUid(tkruleEntity.getPeopleid());

            if (ObjectUtil.isEmpty(people) || people.getId() < 2){
                return "无此维护人员";
            }

            if (ObjectUtil.isEmpty(tkruleEntity.getExpiretime()) ||  tkruleEntity.getExpiretime() < 5  || tkruleEntity.getExpiretime() > 240 ){
                return "人工工单过期时间应在5分钟-6小时之间";
            }

        }

        tkRuleDao.update(tkruleEntity);
        return "修改成功";

    }

    @ApiOperation(value = "删除工单策略")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {
        return tkRuleDao.delete(BigInteger.valueOf(id));
    }

}
