package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Author francesca

 * 2025-06-07
 */

@Data
@TableName("warn")
public class WarnEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private BigInteger id;


    private String name;


    private String alias;


    private Integer level;


    private BigInteger subsys;


    private BigInteger area;

    private String descript;


    private static final long serialVersionUID = 1L;
}
