package com.francesca.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.Power5minDao;
import com.francesca.dao.Power5minTotalDao;
import com.francesca.mapper.Power5minMapper;
import com.francesca.mapper.Power5minTotalMapper;
import com.francesca.model.DTO.Power5minEntity;
import com.francesca.model.DTO.Power5minTotalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author francesca

 * 2025-07-03
 */



@Repository
public class Power5minTotalDaoImpl extends ServiceImpl<Power5minTotalMapper, Power5minTotalEntity> implements Power5minTotalDao {

    @Autowired
    private Power5minTotalMapper power5minTotalMapper;


    @Override
    public List<Power5minTotalEntity> selectbydate(LocalDate defDate) {

        return power5minTotalMapper.selectList(new LambdaQueryWrapper<Power5minTotalEntity>()
                .ge(Power5minTotalEntity::getTime, defDate.atStartOfDay())  // 大于等于当天开始时间
                .lt(Power5minTotalEntity::getTime, defDate.plusDays(1).atStartOfDay())
        );  // 小于下一天开始时间

    }

    @Override
    public void insert(Power5minTotalEntity entity) {
          save(entity);
    }

}