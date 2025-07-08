package com.francesca.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.Power5minDao;
import com.francesca.mapper.Power5minMapper;
import com.francesca.model.DTO.Power5minEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author francesca

 * 2025-07-03
 */



@Repository
public class Power5minDaoImpl extends ServiceImpl<Power5minMapper, Power5minEntity> implements Power5minDao {

    @Autowired
    private Power5minMapper power5minMapper;


    @Override
    public List<Power5minEntity> selectbydate(LocalDate defDate) {

        return power5minMapper.selectList(new LambdaQueryWrapper<Power5minEntity>()
                .ge(Power5minEntity::getTime, defDate.atStartOfDay())  // 大于等于当天开始时间
                .lt(Power5minEntity::getTime, defDate.plusDays(1).atStartOfDay())
        );  // 小于下一天开始时间

    }

    @Override
    public void insert(Power5minEntity entity) {
          save(entity);
    }

}