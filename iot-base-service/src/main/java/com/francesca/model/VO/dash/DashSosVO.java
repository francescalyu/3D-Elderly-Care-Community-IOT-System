package com.francesca.model.VO.dash;

/**
 * @Author francesca

 * 2025-05-16
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel("dashboard - sos")
public class DashSosVO {

    @ApiModelProperty(" 紧急呼救器状态  0-正常  1-求救 " )
    private String status;

    @ApiModelProperty("呼救器五分钟内按下计数   单位 次   " )
    private String count;

    @ApiModelProperty(" 上次按下呼救器时间   " )
    private String pressTime;

    @ApiModelProperty(" 呼救发生时间  " )
    private String sosTime;

    @ApiModelProperty(" 在离线   0-离线  1-在线  " )
    private String online;
}
