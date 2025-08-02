package com.francesca.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.francesca.dao.TicketDao;
import com.francesca.dao.TkRuleDao;
import com.francesca.mapper.TicketMapper;
import com.francesca.mapper.TkRuleMapper;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.TicketEntity;
import com.francesca.model.DTO.TkruleEntity;
import com.francesca.model.DTO.WarnRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.security.krb5.internal.Ticket;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TicketDaoImpl extends ServiceImpl<TicketMapper, TicketEntity> implements TicketDao {

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<TicketEntity> selectAll() {
        return list(null);
    }

    @Override
    public List<TicketEntity> selectByDate(LocalDate defDate) {
        return ticketMapper.selectList(new LambdaQueryWrapper<TicketEntity>()
                .ge(TicketEntity::getCreatetime, defDate.atStartOfDay())  // 大于等于当天开始时间
                .lt(TicketEntity::getCreatetime, defDate.plusDays(1).atStartOfDay())
        );  // 小于下一天开始时间
    }

    @Override
    public List<TicketEntity> selectByMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4));

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusSeconds(1);

        LambdaQueryWrapper<TicketEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.between(TicketEntity::getCreatetime, start, end);

        return list(wrapper);
    }

    @Override
    public TicketEntity selectByUid(BigInteger uid) {
        LambdaQueryWrapper<TicketEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketEntity::getId, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public TicketEntity selectByWarnId(BigInteger uid) {
        LambdaQueryWrapper<TicketEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketEntity::getWarnrecord, ""+uid)
                .last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public void insert(TicketEntity ticketEntity) {
        save(ticketEntity);
    }

    @Override
    public void update(TicketEntity entity) {
        if (ObjectUtil.isEmpty(entity.getId())) {
            log.warn("id is empty, update fail ");
            return;
        }
        update(entity,
                Wrappers.<TicketEntity>lambdaUpdate()
                        .eq(TicketEntity::getId, entity.getId())
        );
    }

    @Override
    public boolean delete(BigInteger id) {
        LambdaUpdateWrapper<TicketEntity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TicketEntity::getId, id);
        return remove(wrapper);
    }
}
