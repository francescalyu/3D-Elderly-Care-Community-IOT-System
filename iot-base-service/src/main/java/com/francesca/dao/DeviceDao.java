package com.francesca.dao;

import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.ProductEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-05-26
 */

public interface DeviceDao {

    List<DeviceEntity> selectAll();

    DeviceEntity selectByUid(BigInteger id);

    void  insert(DeviceEntity entity);

    void  update(DeviceEntity entity);

    boolean  delete(int id);

}
