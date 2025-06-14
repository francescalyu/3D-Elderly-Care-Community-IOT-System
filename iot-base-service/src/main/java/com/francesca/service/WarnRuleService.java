package com.francesca.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.francesca.model.DTO.WarnRuleEntity;
import com.francesca.model.VO.Device.Device;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 点位上报器
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface WarnRuleService{


    List<String> getWarnPoint(List<WarnRuleEntity> warnRuleEntities );


    void execWarnRule(BigInteger devId , BigInteger prod , Object dev , int closeOpen );

}
