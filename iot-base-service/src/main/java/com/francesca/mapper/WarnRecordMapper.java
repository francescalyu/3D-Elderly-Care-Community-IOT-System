package com.francesca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.francesca.model.DTO.WarnEntity;
import com.francesca.model.DTO.WarnRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author francesca

 * 2025-06-07
 */

@Mapper
public interface WarnRecordMapper extends BaseMapper<WarnRecordEntity> {
}