package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author francesca

 * 2025-07-03
 */

@Data
@TableName("power5min")
public class Power5minEntity implements Serializable {
    /**
     * id
     */
    private BigInteger id;

    private String energytoday;

    private String currentpower;

    private String powersave;

    private String co2;

    private String tree;

    private String acpower;

    private String lightpower;

    private String electpower;

    private Date time;


    private static final long serialVersionUID = 1L;
}
