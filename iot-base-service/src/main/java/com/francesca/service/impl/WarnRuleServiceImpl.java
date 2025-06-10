package com.francesca.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.service.CacheService;
import com.francesca.service.CommonService;
import com.francesca.service.WarnRuleService;
import com.francesca.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.beans.PropertyDescriptor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class WarnRuleServiceImpl implements WarnRuleService {

    @Autowired
    private WarnRuleDao warnRuleDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private WarnDao warnDao;

    @Autowired
    private WarnRecordDao warnRecordDao;

    @Autowired
    private CommonService commonService;


    public Map<Integer, List<WarnRuleEntity>> selectWarnRuleByProd(){

             Map<Integer, List<WarnRuleEntity>> out = new HashMap<>();

             List<WarnRuleEntity> warnRuleEntities = warnRuleDao.selectAll();

             warnRuleEntities.stream().forEach(v -> {
                 if (ObjectUtil.isEmpty(out.get(v.getProd()))){
                     out.put(v.getProd(), new ArrayList<>());
                 }
                 List<WarnRuleEntity> warnRuleEntities1 = out.get(v.getProd());
                 warnRuleEntities1.add(v);

             });

             return out;
    }

    @Override
    public List<String> getWarnPoint(List<WarnRuleEntity> warnRuleEntities ){

        List<String> points = new ArrayList<>();

        if(ObjectUtil.isEmpty(warnRuleEntities)){
            return null;
        }

        warnRuleEntities.stream().forEach(v -> {
            if (ObjectUtil.isNotEmpty(v.getPid())){
                PointEntity pointEntity = cacheService.getPoint(v.getPid());

                if (ObjectUtil.isNotEmpty(pointEntity)){
                    points.add(pointEntity.getName());
                }

            }

        });

        return points;
    }





    public  String formatWarnRule(String pv , WarnRuleEntity warnRuleEntity , int area  , int subsys , int dev){

        String out = " ";

            if (ObjectUtil.isNotEmpty( warnRuleEntity.getPid())){
                pv = CommonUtil.toNumberStr(pv);

                out = out + " (";
                out = out + " " + pv;
                out = out + " " + warnRuleEntity.getOp();
                out = out + " " + warnRuleEntity.getPvalue() + " )";

                if ( ObjectUtil.isNotEmpty(warnRuleEntity.getConn())){
                    out = out + " " + warnRuleEntity.getConn() ;
                }

                return out;

            }


            out = out  + " (";


            if (ObjectUtil.isNotEmpty(warnRuleEntity.getArea()) && warnRuleEntity.getArea() > 0 ){
                 out = out + area + " == " + warnRuleEntity.getPvalue();
            }


            if (ObjectUtil.isNotEmpty(warnRuleEntity.getSubsys()) && warnRuleEntity.getSubsys() > 0 ){
                out = out + subsys + " == " + warnRuleEntity.getOp();
            }

            if (ObjectUtil.isNotEmpty(warnRuleEntity.getDev()) && warnRuleEntity.getDev() > 0 ){
                out = out + dev + " == " + warnRuleEntity.getPvalue();
            }

            out = out + " ) ";

            if (ObjectUtil.isNotEmpty(warnRuleEntity.getConn())){
                out = out + " " + warnRuleEntity.getConn();
            }

            return out;

        }



        @Override
        public void execWarnRule(BigInteger devId , BigInteger prod,  Object dev , int closeOpen ){

            // div warnRule by ruleId
            List<WarnRuleEntity> warnOpen = cacheService.readWarnRule(prod,closeOpen);
            Map<BigInteger, List<WarnRuleEntity>> warnRuleByidMap = new HashMap<>();


            for(WarnRuleEntity warnRuleEntity : warnOpen){
                List<WarnRuleEntity> temp = warnRuleByidMap.get(warnRuleEntity.getRuleid());
                if (ObjectUtil.isEmpty(temp)){
                    temp = new ArrayList<>();
                }
                temp.add(warnRuleEntity);
                warnRuleByidMap.put(warnRuleEntity.getRuleid(), temp);
            }

            //exec warn by warn rule


            DeviceEntity device = cacheService.getDevice(devId);
            for (Map.Entry<BigInteger, List<WarnRuleEntity>> entry : warnRuleByidMap.entrySet()) {


                List<WarnRuleEntity> warnRules = entry.getValue();
                List<String> outs = new ArrayList<>();

                for (WarnRuleEntity warnRuleEntity : warnRules) {
                    String out = "";

                    // 规则产品与当前设备产品不同时， 取所有指定产品的值与规则值比较，取AND
                    if (ObjectUtil.isNotEmpty(warnRuleEntity.getProd()) && warnRuleEntity.getProd() > 0 && warnRuleEntity.getProd() != prod.intValue()) {
                        Map<BigInteger, String> pvalues = commonService.getPointValueByProd(BigInteger.valueOf(warnRuleEntity.getProd()), warnRuleEntity.getPid());

                        int i = 0;
                        for (String pv : pvalues.values()) {

                            if (ObjectUtil.isNotEmpty(pvalues)) {
                                out = out + "(";

                                out = out + CommonUtil.toNumberStr(pvalues.get(i));

                                out = out + warnRuleEntity.getOp();

                                out = out + warnRuleEntity.getPvalue();

                                out = out + " && ";
                            }

                        }

                        //去掉最后的" && "
                        if (out.length() > 4) {
                            out = out.substring(0, out.length() - 4);
                        }

                    } else {


                        if (ObjectUtil.isNotEmpty(device)) {
                            String pv = commonService.getPointValue(devId, warnRuleEntity.getPid());

                            out = formatWarnRule(pv, warnRuleEntity, device.getArea(), device.getSubsys(), device.getId().intValue());

                        }
                    }

                    outs.add(out);
                }


                String out = "";
                if (ObjectUtil.isNotEmpty(outs)){
                    for (String v : outs){
                        out = out + v;
                    }
                }


                if (evaluateCondition(out)) {

                    WarnEntity warn = cacheService.readWarn(BigInteger.valueOf(entry.getValue().get(0).getWarnid()));

                    openCloseWarn(entry.getValue(), device, warn.getLevel(), closeOpen);
                }

                log.info("exec warn " + closeOpen + " rule: " + out + " result : " + String.valueOf(evaluateCondition(out)));

            }

        }

    private  void openCloseWarn(List<WarnRuleEntity> rules , DeviceEntity device , int level , int warn){

        if (ObjectUtil.isEmpty( rules)){
            return;
        }
         WarnEntity warnEntity = cacheService.readWarn(BigInteger.valueOf(rules.get(0).getWarnid())) ;

        if (ObjectUtil.isEmpty(warnEntity)){
            return;
        }

        List<WarnRecordEntity> warns = warnRecordDao.selectByDev(device.getId(), warnEntity.getId());

        // exec open warn
        if (warn == 1) {

            if (ObjectUtil.isEmpty(warns)) {

                // open a new warn

                WarnRecordEntity warnRecordEntity = new WarnRecordEntity();
                warnRecordEntity.setId(BigInteger.valueOf(0));
                warnRecordEntity.setStatus(1);
                warnRecordEntity.setArea(BigInteger.valueOf(device.getArea()));
                warnRecordEntity.setLevel(warnEntity.getLevel());
                warnRecordEntity.setName(warnEntity.getName());
                warnRecordEntity.setAlias(warnEntity.getAlias());
                warnRecordEntity.setCount(0);
                warnRecordEntity.setRuleid(rules.get(0).getRuleid());
                warnRecordEntity.setCreateTime(DateUtil.date());
                warnRecordEntity.setSubsys(BigInteger.valueOf(device.getSubsys()));
                warnRecordEntity.setWarnid(BigInteger.valueOf(rules.get(0).getWarnid()));
                warnRecordEntity.setDevid(device.getId());
                warnRecordEntity.setLevel(level);

                warnRecordEntity = isOpen(rules.get(0), warnRecordEntity);
                warnRecordDao.insert(warnRecordEntity);

            }else {

                for (WarnRecordEntity warnRecordEntity : warns) {

                    if (warnRecordEntity.getStatus() == 1) {
                        warnRecordEntity = isOpen(rules.get(0), warnRecordEntity);
                        warnRecordDao.update(warnRecordEntity);

                    }

                }
            }
        }


        if (warn == 0 ){

            if(ObjectUtil.isNotEmpty(warns) &&  warns.size() > 0 ){

                for(WarnRecordEntity warnRecordEntity : warns){

                    warnRecordEntity.setStatus(0);
                    warnRecordEntity.setCloseTime(DateUtil.date());
                }
            }

        }

    }

    private WarnRecordEntity isOpen(WarnRuleEntity rule , WarnRecordEntity record){

        // if rule is use count,  check count is exceed limit or not

        if (ObjectUtil.isEmpty(record.getCount())){
            record.setCount(0);
        }
        record.setCount(record.getCount() + 1 );

        if (ObjectUtil.isNotEmpty(rule.getCount())) {

            if (rule.getCount() <= record.getCount() ){
                record.setStatus(2);
            }
        }



        // if rule is use time ,check time is  exceed time limit or not

        long last =  DateUtil.between( DateUtil.date(), record.getCreateTime() , DateUnit.MINUTE);
        BigInteger delay = BigInteger.valueOf(last);
        record.setDelay(delay);

        if(ObjectUtil.isNotEmpty(rule.getTime()) ){
          if (rule.getTime() <= delay.intValue() ){
                record.setStatus(2);
            }
       }
        return record;
    }

    private static boolean evaluateCondition(String condition) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js"); // 使用 JavaScript 引擎

        try {
            return (boolean) engine.eval(condition); // 返回条件结果
        } catch (ScriptException e) {
            e.printStackTrace();
            log.info(" condition error : " + condition );
            return false;
        }
    }


}
