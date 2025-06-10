package com.francesca.model.VO.product;

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
@ApiModel("product - point")
public class ProdPoint {

    @ApiModelProperty("点位id" )
    private String id;

    @ApiModelProperty("产品id" )
    private String prodid;

    @ApiModelProperty("点位名" )
    private String name;

    @ApiModelProperty("别名" )
    private String alias;

    @ApiModelProperty("点位值" )
    private String value;

    @ApiModelProperty("单位" )
    private String unit;

    @ApiModelProperty("点位类型  1 - 实点   2- 逻辑点   3-告警点  " )
    private String type;

    @ApiModelProperty("点位用途   1 - 采集点  2-控制点  3-采集控制  " )
    private String ptUse;

    @ApiModelProperty("控制指令说明" )
    private String cmd;

    @ApiModelProperty("指令参数下限" )
    private String low;

    @ApiModelProperty("指令参数上限" )
    private String high;

    @ApiModelProperty("指令参数步进值" )
    private String step;


}
