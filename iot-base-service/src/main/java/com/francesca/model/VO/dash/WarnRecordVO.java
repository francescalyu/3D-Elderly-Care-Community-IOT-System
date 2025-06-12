package com.francesca.model.VO.dash;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author francesca

 * 2025-06-12
 */

@Data
public class WarnRecordVO  {

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
    @ApiModelProperty(" 告警级别  1- 告警  2-严重 " )
    private Integer level;

    /**
     * 子系统ID
     */
    @ApiModelProperty(" 所属子系统 " )
    private String subsys;

    /**
     * 区域ID
     */
    @ApiModelProperty(" 发生空间 " )
    private String area;

    /**
     * 状态 (1-create 2-open 3-has ticket 4-inform 0-close)
     */

    @ApiModelProperty(" 告警状态  1-预警  2-打开 3-已派工单  4-已通知  0-关闭   " )
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(" 发生时间   " )
    private Date createTime;

    /**
     * 关闭时间
     */
    @ApiModelProperty(" 关闭时间 " )
    private Date closeTime;

    /**
     * 计数
     */
    @ApiModelProperty(" 设备告警计数 " )
    private Integer count;

    /**
     * 规则ID
     */
    @ApiModelProperty(" 触发规则号 " )
    private BigInteger ruleid;

    /**
     * 告警ID
     */
    @ApiModelProperty(" 告警定义号 " )
    private BigInteger warnid;

    /**
     * 设备ID
     */
    @ApiModelProperty(" 触发设备号 " )
    private BigInteger devid;


    @ApiModelProperty(" 设备名称 " )
    private String devName;


    @ApiModelProperty(" 持续时长   分钟 " )
    private BigInteger delay;


    private static final long serialVersionUID = 1L;
}
