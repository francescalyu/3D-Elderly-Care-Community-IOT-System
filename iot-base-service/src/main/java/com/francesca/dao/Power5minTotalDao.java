package com.francesca.dao;

import com.francesca.model.DTO.Power5minEntity;
import com.francesca.model.DTO.Power5minTotalEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author francesca

 * 2025-07-14
 */

public interface Power5minTotalDao {

    List<Power5minTotalEntity> selectbydate(LocalDate defDate);

    void  insert(Power5minTotalEntity entity);

}
