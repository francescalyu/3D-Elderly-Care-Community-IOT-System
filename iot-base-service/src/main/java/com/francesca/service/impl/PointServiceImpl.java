package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.francesca.model.VO.Device.Device;
import com.francesca.mqtt.ustoneMsg.UStone10AOutletDeserializer;
import com.francesca.mqtt.ustoneMsg.UStone10AOutletModule;
import com.francesca.mqtt.ustoneMsg.UStone10AOutletMsg;
import com.francesca.service.CacheService;
import com.francesca.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
@Slf4j
public class PointServiceImpl implements PointService {

    @Autowired
    private CacheService cacheService;




    @Override
    public void handleMqttMsg(String topic, String msg) throws JsonProcessingException {

       Device device =  cacheService.getDeviceByTopic(topic);
       if (ObjectUtil.isEmpty(device)){
           log.info("============》》No Device find with this topic : " + topic  + " === msg :" + msg );
       }

       if (device.getPid() == BigInteger.valueOf(2)) {
           ObjectMapper mapper = new ObjectMapper();
           mapper.registerModule(new JavaTimeModule());
           mapper.registerModule(new UStone10AOutletModule());

           UStone10AOutletMsg uStone10AOutletMsg = mapper.readValue(msg,UStone10AOutletMsg.class);

           log.info(uStone10AOutletMsg.getStatus().toString());

       }


    }
}
