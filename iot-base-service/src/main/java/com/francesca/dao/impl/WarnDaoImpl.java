package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.WarnDao;
import com.francesca.mapper.WarnMapper;
import com.francesca.model.DTO.WarnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-06-07
 */



@Repository
public class WarnDaoImpl extends ServiceImpl<WarnMapper, WarnEntity> implements WarnDao {

    @Autowired
    private WarnMapper warnMapper;


    @Override
    public List<WarnEntity> selectAll() {
        return list(null);
    }

    @Override
    public WarnEntity selectByUid(BigInteger uid) {
        LambdaQueryWrapper<WarnEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WarnEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }


    @Override
    public void insert(WarnEntity entity) {
           save(entity);
    }

    @Override
    public void update(WarnEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<WarnEntity>lambdaUpdate()
                        .eq(WarnEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(BigInteger id) {
        LambdaUpdateWrapper<WarnEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WarnEntity::getId, id);
        return remove(wrapper);
    }
}