package com.francesca.dao;

import com.francesca.model.DTO.Health1hEntity;
import com.francesca.model.DTO.Power5minEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author francesca

 * 2025-07-11
 */

public interface Health1hDao {

    List<Health1hEntity> selectbydate(LocalDate defDate);

    void  insert(Health1hEntity entity);

}
