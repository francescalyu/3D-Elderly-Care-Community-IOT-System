package com.francesca.dao;

import com.francesca.model.DTO.TkruleEntity;

import java.math.BigInteger;
import java.util.List;

public interface TkRuleDao {

    List<TkruleEntity> selectAll();

    List<TkruleEntity> selectByWarnId(BigInteger warnId);

    TkruleEntity selectByUid(BigInteger id);

    void insert(TkruleEntity entity);

    void update(TkruleEntity entity);


    boolean delete(BigInteger id);


}
