package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @Author francesca
 * 2025-07-23
 */
@Data
@TableName("tkrule")
public class TkruleEntity {

    /**
     * Primary key
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigInteger id;

    /**
     * Rule name
     */
    @ApiModelProperty("策略名称")
    private String name;

    /**
     * Rule alias
     */
    @ApiModelProperty("策略别名")
    private String alias;

    /**
     * Type: 1-auto, 2-manual
     */
    @ApiModelProperty("1 - 自动工单   2- 手工工单")
    private Integer tktype;

    /**
     * Description
     */
    @ApiModelProperty("备注")
    private String descript;

    /**
     * Link to warn
     */

    @ApiModelProperty("关联告警id")
    private BigInteger warnid;

    /**
     * Link to people
     */

    @ApiModelProperty("关联维护人员id")
    private Integer peopleid;

    /**
     * Expire time in minutes
     */
    @ApiModelProperty("过期时间")
    private Integer expiretime;

    /**
     * Execution product ID
     */
    @ApiModelProperty("自动工单  操作产品ID")
    private Integer prodid;

    /**
     * Execution define device ID
     */
    @ApiModelProperty("自动工单  操作设备ID")
    private Integer devid;

    /**
     * Command point
     */
    @ApiModelProperty("自动工单  操作设备点位ID")
    private Integer pid;

    /**
     * Command value
     */
    @ApiModelProperty("自动工单  下发指令点位值")
    private Float cmdvalue;

    /**
     * Retry count (0-99)
     */
    @ApiModelProperty("指令重试次数")
    private Integer retry;
}