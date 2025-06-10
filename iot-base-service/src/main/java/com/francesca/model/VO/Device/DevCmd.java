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
@ApiModel("Device - Cmd")
public class DevCmd {

    @ApiModelProperty("点位id" )
    private String id;

    @ApiModelProperty("设备id" )
    private String devId;

    @ApiModelProperty("命令值" )
    private String pValue;

}
