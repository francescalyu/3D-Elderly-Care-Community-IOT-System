package com.francesca.service;


import com.francesca.model.DTO.WarnRecordEntity;
import com.francesca.model.DTO.WarnRuleEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * 工单执行
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface TicketRuleService {


    Integer execTicketRule(WarnRecordEntity warnRecordEntity);


    Integer execTicketInform(WarnRecordEntity warnRecordEntity);

    Integer execHasInform(WarnRecordEntity warnRecordEntity);

    void  closeTicket(WarnRecordEntity warnRecordEntity);

}
