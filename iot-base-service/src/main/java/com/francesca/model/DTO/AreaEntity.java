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
@TableName("area")
public class AreaEntity implements Serializable {
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


    private static final long serialVersionUID = 1L;
}
