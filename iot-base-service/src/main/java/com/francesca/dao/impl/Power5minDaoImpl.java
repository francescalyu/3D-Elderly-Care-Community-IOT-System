package com.francesca.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.Power5minDao;
import com.francesca.mapper.Power5minMapper;
import com.francesca.model.DTO.Power5minEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
    public List<Power5minEntity> selectbydate(LocalDate defDate , int ptype) {

        return power5minMapper.selectList(new LambdaQueryWrapper<Power5minEntity>()
                .ge(Power5minEntity::getTime, defDate.atStartOfDay())  // 大于等于当天开始时间
                        .eq(Power5minEntity::getPtype,ptype)
                .lt(Power5minEntity::getTime, defDate.plusDays(1).atStartOfDay())
        );  // 小于下一天开始时间

    }

    @Override
    public void insert(Power5minEntity entity) {
          save(entity);
    }

    @Override
    public List<Power5minEntity> selectByMonth(String yearMonth , int ptype) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4));

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusSeconds(1);

        LambdaQueryWrapper<Power5minEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.between(Power5minEntity::getTime, start, end)
                .eq(Power5minEntity::getPtype, ptype);

        return list(wrapper);


    }

}