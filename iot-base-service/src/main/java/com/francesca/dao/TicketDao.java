package com.francesca.dao;

import com.francesca.model.DTO.SubsysEntity;
import com.francesca.model.DTO.TicketEntity;
import com.francesca.model.DTO.TkruleEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface TicketDao {

    List<TicketEntity> selectAll();

    List<TicketEntity> selectByDate(LocalDate localDate);

    List<TicketEntity> selectByMonth(String yearMonth);

    TicketEntity selectByUid(BigInteger id);

    TicketEntity selectByWarnId(BigInteger id);

    void insert(TicketEntity ticketEntity);

    void  update(TicketEntity ticketEntity);

    boolean  delete(BigInteger id);


}
