package com.francesca.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.PeopleDao;
import com.francesca.dao.TicketDao;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.Device.Device;
import com.francesca.service.CacheService;
import com.francesca.service.TicketRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketRuleServiceImpl implements TicketRuleService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PeopleDao peopleDao;

    @Autowired
    private TicketDao ticketDao;


    @Override
    public Integer execTicketRule(WarnRecordEntity warnRecordEntity) {

        if (ObjectUtil.isEmpty(warnRecordEntity)){return 0;}

        List<TkruleEntity> rules = cacheService.getTkRules();

        if (ObjectUtil.isEmpty(rules)){return 0 ;}

        TkruleEntity rule = rules.stream().filter(v -> warnRecordEntity.getWarnid().equals(v.getWarnid())).findFirst().orElse(null);

        if (ObjectUtil.isEmpty(rule)) {return 0;}

        TicketEntity ticket = ticketDao.selectByWarnId(warnRecordEntity.getWarnid());

        if (ObjectUtil.isNotEmpty(ticket) && ticket.getStatus() == 1) {
            return 3;
        }

        if (ObjectUtil.isNotEmpty(ticket) && ticket.getStatus() == 2) {
            return 4;
        }

            TicketEntity ticketEntity = new TicketEntity();

            ticketEntity.setExpire(rule.getExpiretime());
            ticketEntity.setName(rule.getName());
            ticketEntity.setAlias(rule.getAlias());
            ticketEntity.setPeopleid(rule.getPeopleid());
            ticketEntity.setInform(formatInform(warnRecordEntity, rule));
            ticketEntity.setWarnrecord(warnRecordEntity.getWarnid());
            ticketEntity.setCmdvalue(rule.getCmdvalue());
            ticketEntity.setPid(rule.getPid());
            ticketEntity.setCreatetime(new Date());
            ticketEntity.setDevid(ticketEntity.getDevid());
            ticketEntity.setProdid(ticketEntity.getProdid());
            ticketEntity.setStatus(1);
            ticketEntity.setTktype(rule.getTktype());
            ticketEntity.setExpire(rule.getExpiretime());

            ticketEntity.setInform(formatInform(warnRecordEntity, rule));

            ticketDao.insert(ticketEntity);

            //return  inform
            return 3;


    }

    @Override
    public Integer execTicketInform(WarnRecordEntity warnRecordEntity) {



         TicketEntity ticket = ticketDao.selectByWarnId(warnRecordEntity.getWarnid());


         //无ticket, 返回原warn status
         if (ObjectUtil.isEmpty(ticket)){
             return warnRecordEntity.getStatus();
         }


         if (ticket.getStatus() == 1  &&  ObjectUtil.isNotEmpty(ticket.getPeopleid()) ){

             ticket.setStatus(2);
             ticketDao.update(ticket);

             return  4;

         }else {

             ticket.setStatus(3);
             ticket.setClosetime(DateUtil.date());
             ticketDao.update(ticket);
             return 0;

         }

    }

    @Override
    public Integer execHasInform(WarnRecordEntity warnRecordEntity) {
        TicketEntity ticket = ticketDao.selectByWarnId(warnRecordEntity.getWarnid());

        //判断ticket是否超时

          long min =   DateUtil.between(ticket.getCreatetime(), new Date() , DateUnit.MINUTE) ;

          if (min > ticket.getExpire()){
              ticket.setStatus(3);
              ticket.setClosetime(new Date());
              ticketDao.update(ticket);
              return 0;
          }

          return 4;

    }

    @Override
    public void closeTicket(WarnRecordEntity warnRecordEntity) {
        TicketEntity ticket = ticketDao.selectByWarnId(warnRecordEntity.getWarnid());

        if (ObjectUtil.isNotEmpty(ticket) && ticket.getStatus() != 3){
            ticket.setStatus(3);
            ticket.setClosetime(new Date());
            ticketDao.update(ticket);
        }

        return;

    }

    private String formatInform(WarnRecordEntity warnRecordEntity, TkruleEntity tkrule){

        StringBuilder res = new StringBuilder();

        res.append("告警内容:" + warnRecordEntity.getAlias() + "  " + "<br>" );
        res.append("发生时间:" + warnRecordEntity.getCreateTime() + " " +  "<br>" );

        DeviceEntity dev = cacheService.getDevice(warnRecordEntity.getDevid());
        if (ObjectUtil.isNotEmpty(dev)) {
            res.append("关联设备:" + dev.getAlias() + "  设备号" + warnRecordEntity.getDevid() + "  <br>" );
        }

        if (tkrule.getTktype() == 2){

            PeopleEntity peopleEntity = peopleDao.selectByUid(tkrule.getPeopleid());

            if (ObjectUtil.isNotEmpty(peopleEntity)){

                res.append("通知人员:" +  peopleEntity.getAlias() + "  " +  peopleEntity.getName()   + "  <br>");
                res.append(" 通知时间" + DateUtil.now());

            }

        }else {

            if (ObjectUtil.isNotEmpty(tkrule.getDevid())){

            DeviceEntity device = cacheService.getDevice(BigInteger.valueOf(tkrule.getDevid()));

              if (ObjectUtil.isNotEmpty(device)) {

                res.append(" 发送设备控制: " + device.getAlias() + "  设备号:" + device.getId() + "  <br>");
                res.append(" 控制点位:  " + tkrule.getPid() + "  指令值:" + tkrule.getCmdvalue() + "  <br>");
                res.append(" 控制时间: " + DateUtil.now() + "  <br>");
              }
            }

            else {

                if (ObjectUtil.isNotEmpty(tkrule.getProdid())) {
                    ProductEntity product = cacheService.getProduct(BigInteger.valueOf(tkrule.getProdid()));

                    if (ObjectUtil.isNotEmpty(product)) {
                        res.append(" 关闭所有: " + product.getAlias() + "  设备号:");

                        List<Device> alldev = cacheService.getAllDevice();
                        List<Device> findDev = alldev.stream().filter(v -> v.getPid().equals(product.getId())).collect(Collectors.toList());

                        StringBuilder devid = new StringBuilder();

                        if (ObjectUtil.isNotEmpty(findDev)) {
                            findDev.stream().forEach(v -> devid.append(v.getId()).append(" ,"));
                        }

                        res.append(devid + "  <br>");

                        res.append(" 控制点位:  " + tkrule.getPid() + "  指令值:" + tkrule.getCmdvalue() + "  <br>");
                        res.append(" 控制时间: " + DateUtil.now() + "  <br>");


                    }
                }
            }



        }

        return res.toString();

    }

}
