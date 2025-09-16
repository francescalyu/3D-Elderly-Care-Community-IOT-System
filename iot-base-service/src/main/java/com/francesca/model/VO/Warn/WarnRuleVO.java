package com.francesca.model.VO.Warn;

/**
 * @Author francesca

 * 2025-05-16
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@ApiModel("warn - rule")
public class WarnRuleVO {

    @ApiModelProperty("id" )
    private Integer id;

    @ApiModelProperty("告警规则id 重复为同一条规则  " )
    private Integer ruleid;


    @ApiModelProperty("warnId" )
    private Integer warnId;

    @ApiModelProperty("告警级别 1-告警 2-严重" )
    private Integer level;

    @ApiModelProperty("规则名称" )
    private String name;

    @ApiModelProperty("别名" )
    private String alias;

    @ApiModelProperty("生效时间计量 > 分钟数" )
    private Integer time;

    @ApiModelProperty("生效计数计量 >  次 " )
    private Integer count;

    @ApiModelProperty(" 是否限制空间 " )
    private Integer area;


    @ApiModelProperty(" 是否限制子系统 " )
    private Integer subsys;


    @ApiModelProperty(" 告警类型 1-告警  2-节能 " )
    private Integer type;


    @ApiModelProperty("是否启用  0-不启用 1-启用 ")
    private Integer isEnable;

    @ApiModelProperty("规则条件")
    private List<WarnRuleItem> itemList;


}
