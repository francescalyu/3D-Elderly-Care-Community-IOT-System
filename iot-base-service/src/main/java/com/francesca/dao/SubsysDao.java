package com.francesca.dao;

import com.francesca.model.DTO.SubsysEntity;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */

public interface SubsysDao {

    List<SubsysEntity> selectAll();

    SubsysEntity selectByUid(int id);

    void  insert(SubsysEntity subsysEntity);

    void  update(SubsysEntity subsysEntity);

    boolean  delete(int id);

}
