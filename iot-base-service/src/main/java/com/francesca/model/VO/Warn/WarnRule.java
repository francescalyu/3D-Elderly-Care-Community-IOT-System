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
@ApiModel("warn - rule")
public class WarnRule {

    @ApiModelProperty("id" )
    private String id;

    @ApiModelProperty("告警规则id 重复为同一条规则  " )
    private String ruleid;

    @ApiModelProperty("规则名称" )
    private String name;

    @ApiModelProperty("别名" )
    private String alias;

    @ApiModelProperty("运算点位名 " )
    private String point;

    @ApiModelProperty(" 设备 " )
    private String dev;

    @ApiModelProperty(" 产品 " )
    private String prod;

    @ApiModelProperty(" 空间 " )
    private String area;

    @ApiModelProperty(" 子系统 " )
    private String subsys;

    @ApiModelProperty(" >  <  =  >= <=   " )
    private String op;

    @ApiModelProperty(" 规则连接符  " )
    private String conn;

    @ApiModelProperty("取值" )
    private String opValue;

    @ApiModelProperty("时间计量 > 分钟数" )
    private String time;

    @ApiModelProperty("计数计量 >  次 " )
    private String count;

    @ApiModelProperty("告消警  0-消警  1-告警 " )
    private String closeWarn;


}
