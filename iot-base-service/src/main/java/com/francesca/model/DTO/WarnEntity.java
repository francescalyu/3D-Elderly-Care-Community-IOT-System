package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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


    @ApiModelProperty("告警名称")
    private String name;

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("告警等级  1-告警  2-严重")
    private Integer level;

    @ApiModelProperty("所属子系统")
    private BigInteger subsys;

    @ApiModelProperty("所属空间")
    private BigInteger area;

    @ApiModelProperty("备注")
    private String descript;


    private static final long serialVersionUID = 1L;
}
