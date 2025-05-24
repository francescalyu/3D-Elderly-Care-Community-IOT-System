package com.francesca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.SubsysEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author francesca

 * 2025-05-24
 */

@Mapper
public interface ProductMapper extends BaseMapper<ProductEntity> {
}