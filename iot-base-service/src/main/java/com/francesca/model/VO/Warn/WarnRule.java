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

    @ApiModelProperty(" 关联产品 " )
    private String prod;


    @ApiModelProperty("告消警  0-消警  1-告警 " )
    private String closeWarn;

    @ApiModelProperty("运算点位名 " )
    private String point;

    @ApiModelProperty(" >  <  =  >= <=   " )
    private String op;

    @ApiModelProperty("取值" )
    private String opValue;


    @ApiModelProperty("生效时间计量 > 分钟数" )
    private String time;

    @ApiModelProperty("生效计数计量 >  次 " )
    private String count;


    @ApiModelProperty(" 规则连接符  " )
    private String conn;

    @ApiModelProperty(" 是否限制 设备 " )
    private String dev;


    @ApiModelProperty(" 是否限制空间 " )
    private String area;

    @ApiModelProperty(" 是否限制子系统 " )
    private String subsys;










}
