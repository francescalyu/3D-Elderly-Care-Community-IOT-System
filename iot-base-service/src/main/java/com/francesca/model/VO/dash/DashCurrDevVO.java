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
@ApiModel("dashboard - dev")
public class DashCurrDevVO {

    @ApiModelProperty("日期" )
    private String currDate;

    @ApiModelProperty("设备总数" )
    private String totalDev;

    @ApiModelProperty("离线设备数" )
    private String offlineDevs;

}
