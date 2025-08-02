package com.francesca.model.VO.Warn;

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
@ApiModel("节能统计")
public class EnergySavingTotal {

    @ApiModelProperty("当月告警总数" )
    private String warnTotal;

    @ApiModelProperty("当月已派单" )
    private String warnTicket;

    @ApiModelProperty("当月已处理 " )
    private String warnClose;

}
