package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author francesca

 * 2025-06-07
 */

@Data
@TableName("warnrecord")
public class WarnRecordEntity implements Serializable {

    @TableId
    private BigInteger id;

    /**
     * 名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 告警级别
     */
    private Integer level;

    /**
     * 子系统ID
     */
    private BigInteger subsys;

    /**
     * 区域ID
     */
    private BigInteger area;

    /**
     * 状态 (1-create 2-open 3-has ticket 4-inform 0-close)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 关闭时间
     */
    private Date closeTime;

    /**
     * 计数
     */
    private Integer count;

    /**
     * 规则ID
     */
    private BigInteger ruleid;

    /**
     * 告警ID
     */
    private BigInteger warnid;

    /**
     * 设备ID
     */
    private BigInteger devid;

    private BigInteger delay;


    private static final long serialVersionUID = 1L;
}
