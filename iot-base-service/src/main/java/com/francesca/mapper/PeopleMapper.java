package com.francesca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.PeopleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author francesca

 * 2025-05-24
 */

@Mapper
public interface PeopleMapper extends BaseMapper<PeopleEntity> {
}