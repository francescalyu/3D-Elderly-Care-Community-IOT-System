package com.francesca.dao;

import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.WarnRuleEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-06-07
 */

public interface WarnRuleDao {

    List<WarnRuleEntity> selectAll();

    WarnRuleEntity selectByUid(BigInteger id);

    List<WarnRuleEntity> selectByProd(BigInteger prodId);

    void  insert(WarnRuleEntity entity);

    void  update(WarnRuleEntity entity);

    boolean  delete(BigInteger id);

}
