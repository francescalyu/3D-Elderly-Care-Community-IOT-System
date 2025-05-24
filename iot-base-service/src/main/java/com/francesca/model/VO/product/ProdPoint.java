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
@ApiModel("product - point")
public class ProdPoint {

    @ApiModelProperty("点位id" )
    private String id;

    @ApiModelProperty("产品id" )
    private String prodid;

    @ApiModelProperty("点位名" )
    private String name;

    @ApiModelProperty("别名" )
    private String alias;

    @ApiModelProperty("点位值" )
    private String value;

    @ApiModelProperty("单位" )
    private String unit;


}
