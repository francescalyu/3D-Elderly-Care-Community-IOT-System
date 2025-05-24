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
    private int  isctrl;

    /**
     * manufacture
     */
    private String  manu;


    /**
     *  manufacture serial number
     */
    private String  sn;


    private static final long serialVersionUID = 1L;
}
