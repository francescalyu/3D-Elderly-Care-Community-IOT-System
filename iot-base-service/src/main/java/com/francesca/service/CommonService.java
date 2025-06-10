package com.francesca.service;

import com.francesca.model.DTO.PointEntity;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface CommonService {

    String getPointValue(BigInteger devId , BigInteger pointId );

    Map<BigInteger, String> getPointValueByProd(BigInteger prodid, BigInteger pointId);

    Map<Integer, List<PointEntity>> getPointByWarnRule();
}
