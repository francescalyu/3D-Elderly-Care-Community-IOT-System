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
@ApiModel("dashboard - door")
public class DashDoorVO {

    @ApiModelProperty(" 上次开关门时间 " )
    private String changeDate;

    @ApiModelProperty("门禁 0 - 关 ， 1 - 开  " )
    private String status;

    @ApiModelProperty("户外光照度  单位 lux  " )
    private String illuminance;

    @ApiModelProperty("户外温度  单位 摄氏度C  " )
    private String temperature;

    @ApiModelProperty("户外湿度  单位 %   " )
    private String humidity;

}
