package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.WarnRecordDao;
import com.francesca.mapper.WarnRecordMapper;
import com.francesca.model.DTO.WarnRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author francesca

 * 2025-06-07
 */



@Repository
public class WarnRecordDaoImpl extends ServiceImpl<WarnRecordMapper, WarnRecordEntity> implements WarnRecordDao {

    @Autowired
    private WarnRecordMapper warnRecordMapper;


    @Override
    public List<WarnRecordEntity> selectAll() {
        return list(null);
    }

    @Override
    public List<WarnRecordEntity> selectByMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4));

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusSeconds(1);

        LambdaQueryWrapper<WarnRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.between(WarnRecordEntity::getCreateTime, start, end).last("limit 200");

        return list(wrapper);
    }

    @Override
    public List<WarnRecordEntity> selectByDate(LocalDate defDate) {
        return warnRecordMapper.selectList(new LambdaQueryWrapper<WarnRecordEntity>()
                .ge(WarnRecordEntity::getCreateTime, defDate.atStartOfDay())  // 大于等于当天开始时间
                .lt(WarnRecordEntity::getCreateTime, defDate.plusDays(1).atStartOfDay())
                .last("limit 200")
        );  // 小于下一天开始时间
    }

    @Override
    public List<WarnRecordEntity> selectByStatus(int status) {
        return warnRecordMapper.selectList(new LambdaQueryWrapper<WarnRecordEntity>()
                .ge(WarnRecordEntity::getStatus, status)
        );
    }

    @Override
    public WarnRecordEntity selectByUid(BigInteger uid) {
        LambdaQueryWrapper<WarnRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WarnRecordEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public List<WarnRecordEntity> selectByDev(BigInteger dev , BigInteger warn) {
        LambdaQueryWrapper<WarnRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WarnRecordEntity::getDevid, ""+dev).eq(WarnRecordEntity::getWarnid, warn ).gt(WarnRecordEntity::getStatus, 0);

        return list(wrapper);
    }

    @Override
    public List<WarnRecordEntity> selectBySubsys(int subsys , int status) {
        LambdaQueryWrapper<WarnRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WarnRecordEntity::getSubsys, ""+subsys).eq(WarnRecordEntity::getStatus, status );

        return list(wrapper);

    }

    @Override
    public void insert(WarnRecordEntity entity) {
           save(entity);
    }

    @Override
    public void update(WarnRecordEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<WarnRecordEntity>lambdaUpdate()
                        .eq(WarnRecordEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(BigInteger id) {
        LambdaUpdateWrapper<WarnRecordEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WarnRecordEntity::getId, id);
        return remove(wrapper);
    }
}