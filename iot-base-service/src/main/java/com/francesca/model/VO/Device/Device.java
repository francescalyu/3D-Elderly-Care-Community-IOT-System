package com.francesca.model.VO.Device;

/**
 * @Author francesca

 * 2025-05-16
 */

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;


@Data
@NoArgsConstructor
@ApiModel("device - device")
public class Device {


    @ApiModelProperty("设备id" )
    private BigInteger id;

    @ApiModelProperty("产品id" )
    private BigInteger pid;

    @ApiModelProperty("产品名" )
    private String product;

    @ApiModelProperty("厂家产品id" )
    private String manuId;

    @ApiModelProperty("厂家" )
    private String manu;

    @ApiModelProperty("设备名称" )
    private String name;

    @ApiModelProperty("设备别名" )
    private String alias;

    @ApiModelProperty("所在区域" )
    private String area;

    @ApiModelProperty("子系统" )
    private String sys;

    @ApiModelProperty("ip" )
    private String ip;

    @ApiModelProperty("订阅topic" )
    private String topicUp;

    @ApiModelProperty("指令topic" )
    private String topicDown;

    @ApiModelProperty("备注" )
    private String desc;

}
