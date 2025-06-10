package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.PointDao;
import com.francesca.dao.ProductDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.service.CacheService;
import com.francesca.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CommonServiceImpl implements CommonService {


    @Autowired
    private PointDao pointDao;


    @Autowired
    private CacheService cacheService;

    @Override
    public String getPointValue(BigInteger devId , BigInteger pointId ) {

        DeviceEntity deviceEntity = cacheService.getDevice(devId);

        if (ObjectUtil.isEmpty(deviceEntity)){
            return  null;
        }

        PointEntity pointEntity = pointDao.selectByUid(pointId.intValue());

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



}
