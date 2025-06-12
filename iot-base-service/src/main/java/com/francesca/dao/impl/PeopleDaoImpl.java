package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.AreaDao;
import com.francesca.dao.PeopleDao;
import com.francesca.mapper.AreaMapper;
import com.francesca.mapper.PeopleMapper;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.PeopleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author francesca

 * 2025-06-12
 */



@Repository
public class PeopleDaoImpl extends ServiceImpl<PeopleMapper, PeopleEntity> implements PeopleDao {

    @Autowired
    private PeopleMapper peopleMapper;


    @Override
    public List<PeopleEntity> selectAll() {
        return list(null);
    }

    @Override
    public PeopleEntity selectByUid(int uid) {
        LambdaQueryWrapper<PeopleEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PeopleEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public PeopleEntity selectByType(int type) {
        LambdaQueryWrapper<PeopleEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PeopleEntity::getType, ""+type)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(PeopleEntity entity) {
           save(entity);
    }

    @Override
    public void update(PeopleEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<PeopleEntity>lambdaUpdate()
                        .eq(PeopleEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(int id) {
        LambdaUpdateWrapper<PeopleEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PeopleEntity::getId, id);
        return remove(wrapper);
    }
}