package com.francesca.model.VO.Warn;

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
@ApiModel("warn - ruleItem")
public class WarnRuleItem {

    @ApiModelProperty(" 关联产品 " )
    private Integer id;

    @ApiModelProperty(" 关联产品 " )
    private Integer prod;

    @ApiModelProperty("告消警  0-消警  1-告警 " )
    private Integer closeWarn;

    @ApiModelProperty("运算点位ID " )
    private Integer point;

    @ApiModelProperty(" >  <  =  >= <=   " )
    private String op;

    @ApiModelProperty("取值" )
    private String opValue;

    @ApiModelProperty(" 规则连接符  " )
    private String conn;

    @ApiModelProperty(" 是否限制 设备 " )
    private Integer dev;

}
