package com.francesca.model.VO.dash;

/**
 * @Author francesca

 * 2025-05-15
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel("驾驶舱7天")
public class Dash7dayPowerVO {

    @ApiModelProperty("能源种类 1-电 2-水 3-燃气  4-标煤 " )
    private String powerType;

    @ApiModelProperty("单位  m3 / kwh / kgce " )
    private String unit;

    @ApiModelProperty("日期" )
    private String powerDate;

    @ApiModelProperty("用电量  单位 kwh/千瓦时" )
    private String powerCurrDay;

}
