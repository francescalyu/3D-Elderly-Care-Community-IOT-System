package com.francesca.service;

import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.VO.Device.DevCmd;
import com.francesca.model.VO.Device.Device;

/**
 * 设备控制
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface DeviceCmd {

    String sndCmd(DevCmd devCmd);

}
