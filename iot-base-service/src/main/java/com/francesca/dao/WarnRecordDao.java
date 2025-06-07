package com.francesca.dao;

import com.francesca.model.DTO.WarnRecordEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-06-07
 */

public interface WarnRecordDao {

    List<WarnRecordEntity> selectAll();

    WarnRecordEntity selectByUid(BigInteger id);

    List<WarnRecordEntity> selectByDev(BigInteger dev , BigInteger warn);

    void  insert(WarnRecordEntity entity);

    void  update(WarnRecordEntity entity);

    boolean  delete(BigInteger id);

}
