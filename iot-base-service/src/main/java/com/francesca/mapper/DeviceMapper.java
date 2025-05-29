package com.francesca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author francesca

 * 2025-05-26
 */

@Mapper
public interface DeviceMapper extends BaseMapper<DeviceEntity> {
}