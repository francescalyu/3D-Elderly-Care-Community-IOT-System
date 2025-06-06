package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.AreaDao;
import com.francesca.dao.WarnRuleDao;
import com.francesca.mapper.AreaMapper;
import com.francesca.mapper.WarnRuleMapper;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.WarnRuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */



@Repository
public class WarnRuleDaoImpl extends ServiceImpl<WarnRuleMapper, WarnRuleEntity> implements WarnRuleDao {

    @Autowired
    private WarnRuleMapper warnRuleMapper;


    @Override
    public List<WarnRuleEntity> selectAll() {
        return list(null);
    }

    @Override
    public WarnRuleEntity selectByUid(BigInteger uid) {
        LambdaQueryWrapper<WarnRuleEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WarnRuleEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(WarnRuleEntity entity) {
           save(entity);
    }

    @Override
    public void update(WarnRuleEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<WarnRuleEntity>lambdaUpdate()
                        .eq(WarnRuleEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(BigInteger id) {
        LambdaUpdateWrapper<WarnRuleEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WarnRuleEntity::getId, id);
        return remove(wrapper);
    }
}