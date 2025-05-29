package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.AreaDao;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.ProductDao;
import com.francesca.dao.SubsysDao;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.SubsysEntity;
import com.francesca.model.VO.Device.Device;
import com.francesca.service.DeviceMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceMsgImpl implements DeviceMsg {

    @Autowired
    private SubsysDao subsysDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private DeviceDao deviceDao;



    @Override
    public Device setDevice(DeviceEntity entity) {

        if (ObjectUtil.isEmpty(entity.getId())){
            return null;
        }

        if (ObjectUtil.isEmpty(deviceDao.selectByUid(entity.getId()))){
            return null;
        }

        ProductEntity productEntity = productDao.selectByUid(entity.getProduct());
        if (ObjectUtil.isEmpty(productEntity)){
            return null;
        }

        SubsysEntity subsysEntity = subsysDao.selectByUid(entity.getSubsys());
        if (ObjectUtil.isEmpty(subsysEntity)){
            return null;
        }

        AreaEntity areaEntity = areaDao.selectByUid(entity.getArea());
        if (ObjectUtil.isEmpty(areaEntity)){
            return null;
        }


        Device device = new Device();

        device.setId(entity.getId());
        device.setIp(entity.getIp());
        device.setDesc(entity.getDescript());
        device.setArea(areaEntity.getAlias());
        device.setManu(productEntity.getManu());
        device.setAlias(entity.getAlias());
        device.setName(entity.getName());
        device.setPid(productEntity.getId());
        device.setSys(subsysEntity.getAlias());
        device.setTopicUp(entity.getMqttuptopic());
        device.setTopicDown(entity.getMqttdowntopic());
        device.setManuId(entity.getManuid());
        device.setProduct(productEntity.getAlias());

        return device;

    }


}
