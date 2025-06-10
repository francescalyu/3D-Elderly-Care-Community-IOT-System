package com.francesca.service;

import java.math.BigInteger;
import java.util.Map;

public interface CommonService {

    String getPointValue(BigInteger devId , BigInteger pointId );

    Map<BigInteger, String> getPointValueByProd(BigInteger prodid, BigInteger pointId);
}
