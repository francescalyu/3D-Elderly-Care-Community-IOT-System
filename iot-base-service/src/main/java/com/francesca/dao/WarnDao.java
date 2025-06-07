package com.francesca.dao;

import com.francesca.model.DTO.WarnEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-06-07
 */

public interface WarnDao {

    List<WarnEntity> selectAll();

    WarnEntity selectByUid(BigInteger id);

    void  insert(WarnEntity entity);

    void  update(WarnEntity entity);

    boolean  delete(BigInteger id);

}
