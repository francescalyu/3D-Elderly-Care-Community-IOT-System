package com.francesca.mqtt.geekopen;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @Author francesca

 * 2025-07-22
 */

@Data
public class GeekOpenACController {
    @ApiModelProperty("空调开关" )
    private String AConoff;

    @ApiModelProperty("空调温度+1")
    private String ACtempadd;

    @ApiModelProperty("空调温度-1")
    private String ACtempsub;

    @ApiModelProperty("空调冷热切换")
    private String AChotcold;

    @ApiModelProperty("空调风档")
    private String ACwind;

    @ApiModelProperty("空调温度")
    private String ACtemp;


}
