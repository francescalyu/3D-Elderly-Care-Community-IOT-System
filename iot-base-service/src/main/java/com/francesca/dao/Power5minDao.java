package com.francesca.dao;

import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.Power5minEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Author francesca

 * 2025-07-03
 */

public interface Power5minDao {

    List<Power5minEntity> selectbydate(LocalDate defDate, int ptype);

    void  insert(Power5minEntity entity);

    List<Power5minEntity> selectByMonth(String yearMonth , int ptype);

}
