package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.ProductDao;
import com.francesca.mapper.DeviceMapper;
import com.francesca.mapper.ProductMapper;
import com.francesca.mapper.SubsysMapper;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.SubsysEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-05-26
 */



@Repository
public class DeviceDaoImpl extends ServiceImpl<DeviceMapper, DeviceEntity> implements DeviceDao {

    @Autowired
    private DeviceMapper deviceMapper;


    @Override
    public List<DeviceEntity> selectAll() {
        return list(null);
    }

    @Override
    public DeviceEntity selectByUid(BigInteger uid) {
        LambdaQueryWrapper<DeviceEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DeviceEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(DeviceEntity entity) {
           save(entity);
    }

    @Override
    public void update(DeviceEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<DeviceEntity>lambdaUpdate()
                        .eq(DeviceEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(int id) {
        LambdaUpdateWrapper<DeviceEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(DeviceEntity::getId, id);
        return remove(wrapper);
    }
}