package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Author francesca

 * 2025-05-24
 */

@Data
@TableName("product")
public class ProductEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private BigInteger id;


    /**
     * name
     */
    @ApiModelProperty("产品名")
    private String name;

    /**
     * alias
     */
    @ApiModelProperty("产品别名")
    private String alias;

    /**
     * alias english
     */
    @ApiModelProperty("产品英文名")
    private String aliasen;


    /**
     * description
     */
    private String descript;

    /**
     *  subsys id
     */
    private int  subsys;

    /**
     * flag of ctrl
     */
    @ApiModelProperty("是否可控制")
    private int  isctrl;

    /**
     * manufacture
     */
    private String  manu;

    /**
     *  manufacture serial number
     */
    private String  sn;

    @ApiModelProperty("产品图片")
    @TableField(value = "picurl")
    private String picurl;

    private static final long serialVersionUID = 1L;

}
