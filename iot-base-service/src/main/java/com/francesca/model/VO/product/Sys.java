package com.francesca.model.VO.product;

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
@ApiModel("product - sys")
public class Sys {

    @ApiModelProperty("子系统id" )
    private String id;

    @ApiModelProperty("子系统名称" )
    private String sysName;

}
