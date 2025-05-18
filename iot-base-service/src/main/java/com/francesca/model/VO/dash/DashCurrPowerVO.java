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
@ApiModel("dashboard - power")
public class DashCurrPowerVO {

    @ApiModelProperty("日期" )
    private String currDate;

    @ApiModelProperty("用电量  单位 kwh/千瓦时" )
    private String powerCurrDay;

    @ApiModelProperty("累计用电量  单位 kwh/千瓦时" )
    private String powerTotal;

    @ApiModelProperty("累计节省电量  单位 kwh/千瓦时" )
    private String powerSave;

    @ApiModelProperty("累计减少碳排放  单位 吨" )
    private String co2Save;

    @ApiModelProperty("等效植树  单位 棵" )
    private String trees;

}
