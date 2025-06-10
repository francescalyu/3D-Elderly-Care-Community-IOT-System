package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Author francesca

 * 2025-05-24
 */

@Data
@TableName("point")
public class PointEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private BigInteger id;

    /**
     * name
     */
    private String name;

    /**
     * alias
     */
    private String alias;

    /**
     * alias english
     */
    private String aliasen;

    /**
     * unit
     */
    private String unit;


    /**
     * product id
     */
    private Integer prodid;

    /**
     * point type
     */
    private Integer type;


    /**
     * point use
     */
    private Integer ptuse;


    /**
     * cmd description
     */
    private String cmd;

    /**
     * cmd low limit
     */

    private Float low;

    private Float high;

    private Float step;


    private static final long serialVersionUID = 1L;
}
