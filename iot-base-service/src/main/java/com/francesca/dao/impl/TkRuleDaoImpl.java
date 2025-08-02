package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.TkRuleDao;
import com.francesca.mapper.TkRuleMapper;
import com.francesca.model.DTO.TkruleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class TkRuleDaoImpl extends ServiceImpl<TkRuleMapper, TkruleEntity> implements TkRuleDao {

    @Autowired
    private TkRuleMapper tkRuleMapper;

    @Override
    public List<TkruleEntity> selectAll() {
        return list(null);
    }

    @Override
    public List<TkruleEntity> selectByWarnId(BigInteger warnId){
        LambdaQueryWrapper<TkruleEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TkruleEntity::getWarnid, ""+warnId);
        return list(wrapper);
    }

    @Override
    public TkruleEntity selectByUid(BigInteger id){
        LambdaQueryWrapper<TkruleEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TkruleEntity::getId, ""+id).last("limit 1");;
        return getOne(wrapper);
    }

    @Override
    public void insert(TkruleEntity entity) {
        save(entity);
    }

    @Override
    public void update(TkruleEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<TkruleEntity>lambdaUpdate()
                        .eq(TkruleEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(BigInteger id) {
        LambdaUpdateWrapper<TkruleEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TkruleEntity::getId, id);
        return remove(wrapper);
    }


}
