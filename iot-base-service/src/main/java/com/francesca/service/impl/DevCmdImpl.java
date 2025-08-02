package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.VO.Device.DevCmd;
import com.francesca.mqtt.geekopen.GeekOpenACController;
import com.francesca.service.CacheService;
import com.francesca.service.DeviceCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DevCmdImpl implements DeviceCmd {

    @Autowired
    private CacheService cacheService;

    @Override
    public String sndCmd(DevCmd devCmd) {

       DeviceEntity device = cacheService.getDevice(new BigInteger(devCmd.getDevId()));

        if (ObjectUtil.isEmpty(device)){
            return " 无此设备";
        }

        PointEntity pointEntity = cacheService.getPoint(new BigInteger(devCmd.getId()));

        if(ObjectUtil.isEmpty(pointEntity)){
            return "点位不存在";
        }

        if (pointEntity.getProdid() != device.getProduct()){
            return "无适配的控制点";
        }

        if (pointEntity.getPtuse() < 2 ){
            return "点位只读，无法控制";
        }

        Float pValue = Float.parseFloat(devCmd.getPValue());

        if (pValue > pointEntity.getHigh() || pValue < pointEntity.getLow()){
            return " 指令值越过设备的可控范围 ";
        }

        if (device.getProduct() == 11 ){
            ConcurrentHashMap<Integer, GeekOpenACController> geekOpenAcs = cacheService.getGeekOpenACController();

            GeekOpenACController geekOpenACController = geekOpenAcs.get(new BigInteger(devCmd.getDevId()).intValue());

            if(ObjectUtil.isEmpty(geekOpenACController)){
                return " 无此设备";
            }

            int cmd = Integer.parseInt(devCmd.getId());
            int temp = Integer.parseInt(geekOpenACController.getACtemp());

            switch (cmd){
                case 49:
                    geekOpenACController.setAConoff(devCmd.getPValue());
                    break;
                case 50:
                     temp = temp+1;
                    geekOpenACController.setACtemp(String.valueOf(temp));
                    break;
                case 51:
                    temp = temp-1;
                    geekOpenACController.setACtemp(String.valueOf(temp));
                    break;
                case 52:
                    geekOpenACController.setAChotcold(devCmd.getPValue());
                    break;
                case 53:
                    geekOpenACController.setACwind(devCmd.getPValue());
                    break;
                default:
                    return "无适配的控制点";

            }
        }

        return "发送成功";

    }



}
