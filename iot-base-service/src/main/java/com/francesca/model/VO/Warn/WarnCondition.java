package com.francesca.model.VO.Warn;

/**
 * @Author francesca

 * 2025-08-05
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel("warn - Condition")
public class WarnCondition {

    @ApiModelProperty("告警规则id 重复为同一条规则  " )
    private Integer ruleid;


    @ApiModelProperty("告消警  0-消警  1-告警 " )
    private Integer closeWarn;

    @ApiModelProperty(" 关联产品 " )
    private Integer prod;

    @ApiModelProperty(" 是否限制 设备 " )
    private Integer dev;

    @ApiModelProperty("运算点位名 " )
    private String point;

    @ApiModelProperty(" >  <  =  >= <=   " )
    private String op;

    @ApiModelProperty("取值" )
    private String opValue;

    @ApiModelProperty(" 规则连接符  " )
    private String conn;


}
