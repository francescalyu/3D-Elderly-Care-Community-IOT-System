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
@ApiModel("电量趋势对象")
public class DashPowerCountVO {

    @ApiModelProperty("时间单位  1 - 小时  2 - 周  3 - 月  4 - 年 " )
    private String timeSelect;

    @ApiModelProperty("时间值" )
    private String timeKey;

    @ApiModelProperty("电量" )
    private String power;

}
