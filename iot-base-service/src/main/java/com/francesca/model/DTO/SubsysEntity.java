package com.francesca.model.DTO;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author francesca

 * 2025-05-16
 */

@Data
@TableName("subsys")
public class SubsysEntity implements Serializable {
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
