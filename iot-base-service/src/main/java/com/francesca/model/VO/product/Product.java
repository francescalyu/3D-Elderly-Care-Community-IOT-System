package com.francesca.model.VO.product;

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
@ApiModel("product - point")
public class Product {

    @ApiModelProperty("产品id" )
    private String id;

    @ApiModelProperty("厂家" )
    private String manu;

    @ApiModelProperty("是否可控 1 - 可控   0 - 不可控 " )
    private String isctrl;

    @ApiModelProperty("产品名称" )
    private String name;

    @ApiModelProperty("别名" )
    private String alias;

    @ApiModelProperty("子系统" )
    private String sys;

    @ApiModelProperty("产品模型" )
    private List<ProdPoint> model;

    @ApiModelProperty("备注" )
    private String desc;

}
