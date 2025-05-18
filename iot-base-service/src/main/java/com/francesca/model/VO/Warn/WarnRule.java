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

    @ApiModelProperty("告警规则id" )
    private String id;

    @ApiModelProperty("规则名称" )
    private String name;

    @ApiModelProperty("关联点位 " )
    private String pid;

    @ApiModelProperty(" >  <  =  >= <=  告警key " )
    private String openKey;

    @ApiModelProperty("告警值" )
    private String openValue;

    @ApiModelProperty("组合告警 0 - 否  1 - 是" )
    private String combine;

    @ApiModelProperty("组合key  and  or " )
    private String comKey;

}
