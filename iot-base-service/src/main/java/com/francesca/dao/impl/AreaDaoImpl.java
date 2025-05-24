package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.AreaDao;
import com.francesca.dao.SubsysDao;
import com.francesca.mapper.AreaMapper;
import com.francesca.mapper.SubsysMapper;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.SubsysEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */



@Repository
public class AreaDaoImpl extends ServiceImpl<AreaMapper, AreaEntity> implements AreaDao {

    @Autowired
    private AreaMapper areaMapper;


    @Override
    public List<AreaEntity> selectAll() {
        return list(null);
    }

    @Override
    public AreaEntity selectByUid(int uid) {
        LambdaQueryWrapper<AreaEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AreaEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(AreaEntity entity) {
           save(entity);
    }

    @Override
    public void update(AreaEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        LambdaUpdateWrapper<AreaEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(AreaEntity::getId, entity.getId());
        update(wrapper);
    }

    @Override
    public boolean delete(int id) {
        LambdaUpdateWrapper<AreaEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(AreaEntity::getId, id);
        return remove(wrapper);
    }
}