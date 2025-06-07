package com.francesca.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.Device.Device;
import com.francesca.service.CacheService;
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

@Service
@Slf4j
public class WarnRuleServiceImpl implements WarnRuleService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PointDao pointDao;

    @Autowired
    private WarnRuleDao warnRuleDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private WarnDao warnDao;


    @Autowired
    private WarnRecordDao warnRecordDao;


    @Override
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
                PointEntity pointEntity = pointDao.selectByUid(v.getPid().intValue());

                if (ObjectUtil.isNotEmpty(pointEntity)){
                    points.add(pointEntity.getName());
                }

            }

        });

        return points;
    }

    @Override
    public  String formatWarnRule(List<String> pvs, List<WarnRuleEntity> warns , int area  , int subsys , int dev){
       // String out = " if  ";
        String out = " ";

        int i = 0;
        for ( String pv : pvs){

            WarnRuleEntity warnRule = warns.get(i);
            if (ObjectUtil.isNotEmpty(warnRule.getPid()) ){

                pv = CommonUtil.toNumberStr(pv);

                out = out + " (";
                out = out + " " + pv;
                out = out + " " + warnRule.getOp();
                out = out + " " + warnRule.getPvalue() + " )";

                if (warns.size() > i &&  i > 0 && ObjectUtil.isNotEmpty(warnRule.getConn())){
                    out = out + " " + warnRule.getConn() ;
                }

                i++;

            }

        }

        if (i >= warns.size()){
            return  out;
        }

        for (int j = i+1 ; j<warns.size() ; j++){

            out = out  + " (";

            WarnRuleEntity warnRule = warns.get(j);

            if (ObjectUtil.isNotEmpty(warnRule.getArea()) && warnRule.getArea() > 0 ){
                 out = out + area + " = " + warnRule.getOp();
            }


            if (ObjectUtil.isNotEmpty(warnRule.getSubsys()) && warnRule.getSubsys() > 0 ){
                out = out + subsys + " = " + warnRule.getOp();
            }

            if (ObjectUtil.isNotEmpty(warnRule.getDev()) && warnRule.getDev() > 0 ){
                out = out + dev + " = " + warnRule.getOp();
            }

            if (ObjectUtil.isNotEmpty(warnRule.getConn())){
                out = out + " " + warnRule.getConn();
            }

        }

        return out;

        }


        @Override
        public void execWarnRule(Device device , Object dev , int closeOpen ){

            // div warnRule by ruleId
            List<WarnRuleEntity> warnOpen = cacheService.readWarnRule(BigInteger.valueOf(2),closeOpen);
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
            warnRuleByidMap.entrySet().forEach(
                    v->{

                        List<String> points = getWarnPoint(v.getValue());
                        List<String> pvs = new ArrayList<>();

                        // read point value from device msg
                        if (ObjectUtil.isNotEmpty(points) && ObjectUtil.isNotEmpty(cacheService.getDevice(device.getId()))){
                            points.stream().forEach(s -> {
                                try {
                                    String pv = (String) getProperty(dev, s);
                                    pvs.add(pv);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            DeviceEntity deviceEntity = cacheService.getDevice(device.getId());

                            // format the warnRule condition
                            String warnIf = formatWarnRule(pvs,  v.getValue(),deviceEntity.getArea(),  deviceEntity.getSubsys(), deviceEntity.getId().intValue());

                            if (evaluateCondition(warnIf)){
                                 if (closeOpen == 1) {

                                     openWarn(v.getValue(), deviceEntity);
                                 }else {
                                     closeWarn(v.getValue(), deviceEntity);
                                 }
                            }

                            log.info(String.valueOf(evaluateCondition(warnIf)));

                        }


                    }
            );


        }

    private  void openWarn(List<WarnRuleEntity> rules , DeviceEntity device){

        if (ObjectUtil.isEmpty( rules)){
            return;
        }
         WarnEntity warnEntity = cacheService.readWarn(BigInteger.valueOf(rules.get(0).getWarnid())) ;

        if (ObjectUtil.isEmpty(warnEntity)){
            return;
        }

        List<WarnRecordEntity> warns =  warnRecordDao.selectByDev(device.getId(), warnEntity.getId());

        if (ObjectUtil.isEmpty(warns)){
            WarnRecordEntity warnRecordEntity = new WarnRecordEntity();
            warnRecordEntity.setStatus( 1 );
            warnRecordEntity.setArea(BigInteger.valueOf(device.getArea()));
            warnRecordEntity.setLevel(warnEntity.getLevel());
            warnRecordEntity.setName(warnEntity.getName());
            warnRecordEntity.setAlias(warnEntity.getAlias());
            warnRecordEntity.setCount(1);
            warnRecordEntity.setRuleId(rules.get(0).getRuleid());
            warnRecordEntity.setCreateTime(DateUtil.date());
            warnRecordEntity.setSubsys(BigInteger.valueOf(device.getSubsys()));

            warnRecordEntity = isOpen(rules.get(0), warnRecordEntity);
            warnRecordDao.insert(warnRecordEntity);

        }

        for (WarnRecordEntity warnRecordEntity : warns){

            if (warnRecordEntity.getStatus() == 1){
                warnRecordEntity = isOpen(rules.get(0), warnRecordEntity);

                if (warnRecordEntity.getStatus() >  1){
                    warnRecordDao.update(warnRecordEntity);
                }
            }

        }

    }

    private WarnRecordEntity isOpen(WarnRuleEntity rule , WarnRecordEntity record){

        if (ObjectUtil.isNotEmpty(rule.getCount()) && rule.getCount() <= record.getCount() ){
            record.setStatus(2);

        }

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


    private void closeWarn(List<WarnRuleEntity> rules , DeviceEntity device){


    }


    private static Object getProperty(Object obj, String propertyName) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        return pd.getReadMethod().invoke(obj);
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
