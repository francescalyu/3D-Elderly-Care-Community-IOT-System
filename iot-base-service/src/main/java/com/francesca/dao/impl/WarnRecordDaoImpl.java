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
import java.util.List;

/**
 * @Author francesca

 * 2025-06-07
 */



@Repository
public class WarnRecordDaoImpl extends ServiceImpl<WarnRecordMapper, WarnRecordEntity> implements WarnRecordDao {

    @Autowired
    private WarnRecordMapper WarnRecordMapper;


    @Override
    public List<WarnRecordEntity> selectAll() {
        return list(null);
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
        wrapper.eq(WarnRecordEntity::getDevId, ""+dev).eq(WarnRecordEntity::getWarnId, warn ).gt(WarnRecordEntity::getStatus, 0);

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