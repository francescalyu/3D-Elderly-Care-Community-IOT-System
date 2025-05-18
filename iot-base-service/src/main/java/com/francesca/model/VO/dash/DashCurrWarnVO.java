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
@ApiModel("dashboard - warn")
public class DashCurrWarnVO {

    @ApiModelProperty("日期" )
    private String currDate;

    @ApiModelProperty("0 - 无 ， 1 - 提示  2 - 严重" )
    private String 告警状态;

    @ApiModelProperty("当日告警数" )
    private String currWarn;

    @ApiModelProperty("当日未处理告警数" )
    private String currOpenWarn;

    @ApiModelProperty("累计告警数" )
    private String totalWarn;


}
