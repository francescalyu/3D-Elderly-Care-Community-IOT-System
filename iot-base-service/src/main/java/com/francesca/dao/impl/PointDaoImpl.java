package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.PointDao;
import com.francesca.dao.SubsysDao;
import com.francesca.mapper.PointMapper;
import com.francesca.mapper.SubsysMapper;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.SubsysEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */



@Repository
public class PointDaoImpl extends ServiceImpl<PointMapper, PointEntity> implements PointDao {

    @Autowired
    private SubsysMapper subsysMapper;


    @Override
    public List<PointEntity> selectAll() {
        return list(null);
    }

    @Override
    public PointEntity selectByUid(int uid) {
        LambdaQueryWrapper<PointEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PointEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public List<PointEntity> selectByProduct(int id) {
        LambdaUpdateWrapper<PointEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PointEntity::getProdid, id);
        return list(wrapper);
    }

    @Override
    public void insert(PointEntity entity) {
           save(entity);
    }

    @Override
    public void update(PointEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<PointEntity>lambdaUpdate()
                        .eq(PointEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(int id) {
        LambdaUpdateWrapper<PointEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PointEntity::getId, id);
        return remove(wrapper);
    }
}