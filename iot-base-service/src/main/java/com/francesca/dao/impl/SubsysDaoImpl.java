package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.SubsysDao;
import com.francesca.mapper.SubsysMapper;
import com.francesca.model.DTO.SubsysEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */



@Repository
public class SubsysDaoImpl extends ServiceImpl<SubsysMapper, SubsysEntity> implements SubsysDao {

    @Autowired
    private SubsysMapper subsysMapper;


    @Override
    public List<SubsysEntity> selectAll() {
        return list(null);
    }

    @Override
    public SubsysEntity selectByUid(int uid) {
        LambdaQueryWrapper<SubsysEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SubsysEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(SubsysEntity subsysEntity) {
           save(subsysEntity);
    }

    @Override
    public void update(SubsysEntity subsysEntity) {
        if (ObjectUtil.isEmpty(subsysEntity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        LambdaUpdateWrapper<SubsysEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SubsysEntity::getId, subsysEntity.getId());
        update(wrapper);
    }

    @Override
    public boolean delete(int id) {
        LambdaUpdateWrapper<SubsysEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SubsysEntity::getId, id);
        return remove(wrapper);
    }
}