package com.francesca.model.VO.Device;

/**
 * @Author francesca

 * 2025-05-16
 */

import com.francesca.model.VO.product.ProdPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@ApiModel("device - device")
public class DevStatus {

    @ApiModelProperty("设备id" )
    private String id;
;
    @ApiModelProperty("厂家" )
    private String manu;

    @ApiModelProperty("产品名称" )
    private String prodName;

    @ApiModelProperty("子系统" )
    private String sys;

    @ApiModelProperty("设备数据" )
    private List<ProdPoint> status;

}
