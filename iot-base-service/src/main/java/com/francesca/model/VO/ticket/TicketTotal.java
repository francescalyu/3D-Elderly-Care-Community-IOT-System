package com.francesca.model.VO.ticket;

/**
 * @Author francesca

 * 2025-07-21
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel("工单统计")
public class TicketTotal {

    @ApiModelProperty("当月未处理" )
    private String ticketRemain;

    @ApiModelProperty("当月已延期" )
    private String ticketExpire;

    @ApiModelProperty("当月已处理 " )
    private String ticketClose;

    @ApiModelProperty("当月人工 " )
    private String ticketManual;

    @ApiModelProperty("当月自动" )
    private String ticketAuto;


}
