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
@TableName("device")
public class DeviceEntity implements Serializable {
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
     * manufacture id
     */
    private String manuid;

    /**
     *  subsys id
     */
    private int  subsys;

    /**
     *  area id
     */
    private int  area;

    /**
     * product id
     */
    private int  product;




    /**
     * product ip
     */
    private String  ip;


    /**
     *  MQTT up topic
     */
    private String  mqttuptopic;

    /**
     *  mqtt down topic
     */
    private String  mqttdowntopic;

    private static final long serialVersionUID = 1L;
}
