package com.francesca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.francesca.model.DTO.TicketEntity;
import com.francesca.model.DTO.TkruleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author francesca

 * 2025-07-23
 */

@Mapper
public interface TicketMapper extends BaseMapper<TicketEntity> {
}