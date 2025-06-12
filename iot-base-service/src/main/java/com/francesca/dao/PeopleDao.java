package com.francesca.dao;

import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.PeopleEntity;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */

public interface PeopleDao {

    List<PeopleEntity> selectAll();

    PeopleEntity selectByUid(int id);

    PeopleEntity selectByType(int type);

    void  insert(PeopleEntity entity);

    void  update(PeopleEntity entity);

    boolean  delete(int id);

}
