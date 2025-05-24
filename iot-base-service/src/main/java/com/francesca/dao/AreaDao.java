package com.francesca.dao;

import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.SubsysEntity;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */

public interface AreaDao {

    List<AreaEntity> selectAll();

    AreaEntity selectByUid(int id);

    void  insert(AreaEntity entity);

    void  update(AreaEntity entity);

    boolean  delete(int id);

}
