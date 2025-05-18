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
@ApiModel("warn - warn")
public class Warn {

    @ApiModelProperty("告警id" )
    private String id;

    @ApiModelProperty("告警内容" )
    private String name;

    @ApiModelProperty("告警级别 1 - 提示  2 - 严重 " )
    private String level;

    @ApiModelProperty("告警状态 0 - 消除  1 - 打开 " )
    private String status;

    @ApiModelProperty("告警时间" )
    private String openTime;

    @ApiModelProperty("消警时间" )
    private String closeTime;

}
