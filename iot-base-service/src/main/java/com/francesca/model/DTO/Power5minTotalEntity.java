package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author francesca

 * 2025-07-14
 */

@Data
@TableName("power5mintotal")
public class Power5minTotalEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private BigInteger id;

    @ApiModelProperty("当日用电量")
    private String energytoday;

    @ApiModelProperty("实时功率")
    private String currentpower;

    @ApiModelProperty("节能率")
    private String powersave;

    @ApiModelProperty("碳减排")
    private String co2;

    @ApiModelProperty("等效植树")
    private String tree;

    @ApiModelProperty("空调用电")
    private String acpower;

    @ApiModelProperty("照明用电")
    private String lightpower;

    @ApiModelProperty("厨电用电")
    private String electpower;

    @ApiModelProperty("记录类型  1-5分钟  2-1小时  3-1天 ")
    private int ptype;


    private Date time;


    private static final long serialVersionUID = 1L;
}
