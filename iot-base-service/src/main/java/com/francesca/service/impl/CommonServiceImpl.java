package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.PointDao;
import com.francesca.dao.WarnRuleDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.WarnRuleEntity;
import com.francesca.service.CacheService;
import com.francesca.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CommonServiceImpl implements CommonService {


    @Autowired
    private PointDao pointDao;


    @Autowired
    private CacheService cacheService;

    @Autowired
    private WarnRuleDao warnRuleDao;

    @Override
    public String getPointValue(BigInteger devId , BigInteger pointId ) {

        DeviceEntity deviceEntity = cacheService.getDevice(devId);

        if (ObjectUtil.isEmpty(deviceEntity)){
            return  null;
        }

        PointEntity pointEntity = cacheService.getPoint(pointId);

        if (ObjectUtil.isEmpty(pointEntity)){
            return null;
        }


        Object dev = null;

        switch (pointEntity.getProdid()){
            case 2:
                dev = cacheService.getUStone10AOutlets().get(devId);
                break;
            case 3:
                dev = cacheService.getUstoneAirSixSensors().get(devId);
                break;
            case 4:
                dev = cacheService.getUstoneSmokeSensors().get(devId);
                break;
            case 7:
                dev = cacheService.getDashDoor();
                break;
            default:
                dev = null;
                break;
      }

      if (ObjectUtil.isEmpty(dev)){
          return null;
      }


           try {
                return String.valueOf( getProperty(dev, pointEntity.getName().trim()) );
           } catch (Exception e) {
                e.printStackTrace();
                return null;
           }

    }

    @Override
    public Map<BigInteger, String> getPointValueByProd(BigInteger prodid, BigInteger pointId) {

        Map<BigInteger, String> out = new HashMap<>();

        switch (prodid.intValue()){
            case 2:
                 cacheService.getUStone10AOutlets().entrySet().stream().forEach(
                         v-> {
                             out.put(v.getKey(), getPointValue(v.getKey(), pointId));
                         }
                 );
                 break;

            case 3:
                cacheService.getUstoneAirSixSensors().entrySet().stream().forEach(
                        v-> {
                            out.put(v.getKey(), getPointValue(v.getKey(), pointId));
                        }
                );
                break;
            case 4:
                cacheService.getUstoneSmokeSensors().entrySet().stream().forEach(
                        v-> {
                            out.put(v.getKey(), getPointValue(v.getKey(), pointId));
                        }
                );
                break;


        }

        return out;
    }


    private static Object getProperty(Object obj, String propertyName) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        return pd.getReadMethod().invoke(obj);
    }

    @Override
    public Map<Integer, List<PointEntity>> getPointByWarnRule() {

        List<WarnRuleEntity> warnRuleEntities = warnRuleDao.selectAll();

        if (warnRuleEntities.isEmpty()){
            return null;
        }

        Map<Integer, List<PointEntity>> out = new HashMap<>();

        for(WarnRuleEntity warnRule : warnRuleEntities  ){



            List<PointEntity> temp = new ArrayList<>();


                if (ObjectUtil.isNotEmpty(warnRule.getPid())  ){
                    PointEntity pointEntity = cacheService.getPoint(warnRule.getPid());
                    if (ObjectUtil.isNotEmpty(pointEntity)){
                        temp.add(pointEntity);
                    }

                }

            if (ObjectUtil.isEmpty(out) ||  ObjectUtil.isEmpty(out.get(warnRule.getRuleid())) ){

                out.put(warnRule.getRuleid().intValue(), temp);
            }else {
                List<PointEntity> ones = out.get(warnRule.getRuleid());
                ones.addAll(temp);

            }


        }

        return out;
    }

}
