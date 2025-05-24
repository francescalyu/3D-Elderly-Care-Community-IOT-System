package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.ProductDao;
import com.francesca.dao.SubsysDao;
import com.francesca.mapper.ProductMapper;
import com.francesca.mapper.SubsysMapper;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.SubsysEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */



@Repository
public class ProductDaoImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductDao {

    @Autowired
    private SubsysMapper subsysMapper;


    @Override
    public List<ProductEntity> selectAll() {
        return list(null);
    }

    @Override
    public ProductEntity selectByUid(int uid) {
        LambdaQueryWrapper<ProductEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(ProductEntity entity) {
           save(entity);
    }

    @Override
    public void update(ProductEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        LambdaUpdateWrapper<ProductEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductEntity::getId, entity.getId());
        update(wrapper);
    }

    @Override
    public boolean delete(int id) {
        LambdaUpdateWrapper<ProductEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductEntity::getId, id);
        return remove(wrapper);
    }
}