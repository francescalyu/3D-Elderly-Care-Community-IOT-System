package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.PeopleDao;
import com.francesca.dao.SubsysDao;
import com.francesca.dao.TicketDao;
import com.francesca.dao.WarnRecordDao;
import com.francesca.model.DTO.PeopleEntity;
import com.francesca.model.DTO.SubsysEntity;
import com.francesca.model.DTO.TicketEntity;
import com.francesca.model.DTO.WarnRecordEntity;
import com.francesca.model.VO.Warn.WarnTotal;
import com.francesca.model.VO.ticket.TicketTotal;
import com.francesca.model.VO.ticket.TicketVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author francesca

 * 2025-07-31
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Ticket)
@Validated
@Api(tags = "工单")
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private WarnRecordDao warnRecordDao;

    @Autowired
    private PeopleDao peopleDao;


    @ApiOperation(value = "获取工单统计")
    @GetMapping( "getTicketTotal")
    public TicketTotal getTicketTotal() {

       LocalDateTime now = LocalDateTime.now();

        // 定义格式为yyyyMMdd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        String yearMonth = now.format(formatter);

        List<TicketEntity> res = ticketDao.selectByMonth(yearMonth);

        TicketTotal ticketTotal = new TicketTotal();
        ticketTotal.setTicketRemain("0");
        ticketTotal.setTicketExpire("0");
        ticketTotal.setTicketExpire("0");
        ticketTotal.setTicketAuto("0");
        ticketTotal.setTicketManual("0");


        if (ObjectUtil.isEmpty(res)){
               return ticketTotal;
        }else {
            int tkRemain = (int) res.stream().filter(v -> v.getStatus() == 1).count();
            int tkClose = (int) res.stream().filter(v -> v.getStatus() == 3).count();
            int tkExpire = (int) res.stream().filter(v -> v.getExpire() == 1).count();
            int tkmanual = (int) res.stream().filter(v -> v.getTktype() == 2).count();
            int tkauto = (int) res.stream().filter(v -> v.getTktype() == 1).count();

            ticketTotal.setTicketRemain(String.valueOf(tkRemain));
            ticketTotal.setTicketClose(String.valueOf(tkClose));
            ticketTotal.setTicketExpire(String.valueOf(tkExpire));
            ticketTotal.setTicketAuto(String.valueOf(tkauto));
            ticketTotal.setTicketManual(String.valueOf(tkmanual));

        }

         return ticketTotal;


    }

    @ApiOperation(value = "列出所有工单")
    @GetMapping( "getall")
    public List<TicketVO> getall() {

         List<TicketEntity> ticketEntities = ticketDao.selectAll();

         List<TicketVO> res = new ArrayList<>();

         ticketEntities.stream().forEach( v -> res.add(setTicket(v)));

         return res;

    }

    @ApiOperation(value = "查看工单详情")
    @GetMapping( "getOne")
    public TicketVO getOne(@RequestParam int id) {

        TicketEntity ticket = ticketDao.selectByUid(BigInteger.valueOf(id));

        if (ObjectUtil.isNotEmpty(ticket)){
            return setTicket(ticket);
        }

        return null;
    }

    @ApiOperation(value = "接受工单通知")
    @PostMapping( "confirm")
    public boolean confirm(@RequestParam int id) {
        TicketEntity ticket = ticketDao.selectByUid(BigInteger.valueOf(id));

        if (ObjectUtil.isNotEmpty(ticket)) {
            ticket.setStatus(2);
            ticketDao.update(ticket);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "手工关闭工单")
    @DeleteMapping( "close")
    public boolean close(@RequestParam int id) {

        TicketEntity ticket = ticketDao.selectByUid(BigInteger.valueOf(id));

        if (ObjectUtil.isNotEmpty(ticket)){
            ticket.setStatus(3);
            ticketDao.update(ticket);

            WarnRecordEntity warnRecord = warnRecordDao.selectByUid(ticket.getWarnrecord());

            if (ObjectUtil.isNotEmpty(warnRecord) && warnRecord.getStatus() != 0){

                warnRecord.setStatus(0);
                warnRecord.setCloseTime(new Date());
                warnRecordDao.update(warnRecord);
            }
            return true;
        }
        return false;

    }



    @ApiOperation(value = "手工生成工单")
    @PostMapping( "add")
    public boolean add(@RequestBody TicketVO ticketVO) {

        TicketEntity ticket = new TicketEntity();

        ticket.setProdid(ticketVO.getProdid());
        ticket.setPid(ticketVO.getPid());
        ticket.setExpire(ticketVO.getExpire());
        ticket.setStatus(1);
        ticket.setInform(ticketVO.getInform());
        ticket.setTktype(ticketVO.getTktype());
        ticket.setDevid(ticketVO.getDevid());
        ticket.setCmdvalue(ticketVO.getCmdvalue());
        ticket.setPeopleid(ticketVO.getPeopleid());
        ticket.setName(ticketVO.getName());
        ticket.setAlias(ticketVO.getAlias());
        ticket.setWarnrecord(ticketVO.getWarnrecord());


        ticketDao.insert(ticket);

        return true;
    }


    private TicketVO setTicket(TicketEntity entity){

        TicketVO ticketVO = new TicketVO();

        ticketVO.setId(entity.getId());
        ticketVO.setClosetime(entity.getClosetime());
        ticketVO.setAlias(entity.getAlias());
        ticketVO.setDevid(entity.getDevid());
        ticketVO.setCmdvalue(entity.getCmdvalue());
        ticketVO.setCreatetime(entity.getCreatetime());
        ticketVO.setExpire(entity.getExpire());
        ticketVO.setInform(entity.getInform());
        ticketVO.setName(entity.getName());
        ticketVO.setInformtime(entity.getInformtime());
        ticketVO.setPid(entity.getPid());
        ticketVO.setPeopleid(entity.getPeopleid());
        ticketVO.setProdid(entity.getProdid());
        ticketVO.setStatus(entity.getStatus());
        ticketVO.setTktype(entity.getTktype());
        ticketVO.setWarnrecord(entity.getWarnrecord());

        if(ObjectUtil.isNotEmpty(entity.getPeopleid())) {
            PeopleEntity people = peopleDao.selectByUid(entity.getPeopleid());
            if (ObjectUtil.isNotEmpty(people)) {
                ticketVO.setPeopleName(people.getName());
            }
        }

        WarnRecordEntity warnRecordEntity = warnRecordDao.selectByUid(entity.getWarnrecord());

        if (ObjectUtil.isNotEmpty(warnRecordEntity)){
            ticketVO.setWarnName(warnRecordEntity.getAlias());
        }

        return ticketVO;
    }


}
