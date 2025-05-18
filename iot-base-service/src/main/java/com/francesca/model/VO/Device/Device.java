package com.francesca.model.VO.Device;

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
@ApiModel("device - device")
public class Device {

    @ApiModelProperty("设备id" )
    private String id;

    @ApiModelProperty("产品id" )
    private String pid;

    @ApiModelProperty("厂家产品id" )
    private String manuId;

    @ApiModelProperty("厂家" )
    private String manu;

    @ApiModelProperty("点位前缀 0或空为无" )
    private String prefix;

    @ApiModelProperty("产品名称" )
    private String prodName;

    @ApiModelProperty("子系统" )
    private String sys;

    @ApiModelProperty("订阅topic" )
    private String topicUp;

    @ApiModelProperty("指令topic" )
    private String topicDown;

}
