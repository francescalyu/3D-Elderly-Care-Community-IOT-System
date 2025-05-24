package com.francesca.dao;

import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.SubsysEntity;

import java.util.List;

/**
 * @Author francesca

 * 2025-05-24
 */

public interface ProductDao {

    List<ProductEntity> selectAll();

    ProductEntity selectByUid(int id);

    void  insert(ProductEntity entity);

    void  update(ProductEntity entity);

    boolean  delete(int id);

}
