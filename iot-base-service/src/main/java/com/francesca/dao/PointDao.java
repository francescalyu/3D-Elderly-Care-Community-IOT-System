package com.francesca.dao;

import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.SubsysEntity;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-26
 */

public interface PointDao {

    List<PointEntity> selectAll();

    PointEntity selectByUid(int id);

    List<PointEntity> selectByProduct(int id);

    void  insert(PointEntity entity);

    void  update(PointEntity entity);

    boolean  delete(int id);

}
