package com.francesca.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.Health1hDao;
import com.francesca.dao.Power5minDao;
import com.francesca.mapper.Health1hMapper;
import com.francesca.mapper.Power5minMapper;
import com.francesca.model.DTO.Health1hEntity;
import com.francesca.model.DTO.Power5minEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author francesca

 * 2025-07-11
 */



@Repository
public class Health1hDaoImpl extends ServiceImpl<Health1hMapper, Health1hEntity> implements Health1hDao {

    @Autowired
    private Health1hMapper health1hMapper;


    @Override
    public List<Health1hEntity> selectbydate(LocalDate defDate) {

        return health1hMapper.selectList(new LambdaQueryWrapper<Health1hEntity>()
                .ge(Health1hEntity::getTime, defDate.atStartOfDay())  // 大于等于当天开始时间
                .lt(Health1hEntity::getTime, defDate.plusDays(1).atStartOfDay())
        );  // 小于下一天开始时间

    }

    @Override
    public void insert(Health1hEntity entity) {
          save(entity);
    }

}