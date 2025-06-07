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
@TableName("warnrule")
public class WarnRuleEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private BigInteger id;

    private BigInteger ruleid;

    private String point;

    private BigInteger pid;

    private String op;

    private String pvalue;

    private String conn;

    private int prod;

    private int dev;

    private int subsys;

    private int area ;

    private Integer time;

    private Integer count;

    private String name;

    private String alias;

    private String descript;

    private Integer closewarn;

    private Integer warnid;


    private static final long serialVersionUID = 1L;
}
