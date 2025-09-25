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
@ApiModel("告警统计")
public class WarnTotal {

    @ApiModelProperty("当月未处理" )
    private String warnTotal;

    @ApiModelProperty("当月已派单" )
    private String warnTicket;

    @ApiModelProperty("当月已处理 " )
    private String warnClose;

    @ApiModelProperty("当前设备告警 " )
    private String warnDeviceToday;

    @ApiModelProperty("当前健康告警" )
    private String warnHealthToday;


}
