package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author francesca

 * 2025-07-11
 */


@Data
@TableName("health1h")
public class Health1hEntity {

    /**
     * Primary key ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Heart rate
     */
    @ApiModelProperty("心率")
    private String heartrate;

    /**
     * Blood pressure
     */
    @ApiModelProperty("参考血压")
    private String bloodpressure;

    /**
     * Body temperature
     */
    @ApiModelProperty("体温")
    private String bodytemp;

    /**
     * Calories burned
     */
    @ApiModelProperty("消耗卡路里  单位 卡")
    private String calories;

    /**
     * Sleep data
     */
    @ApiModelProperty("当日睡眠时间 单位 分")
    private String sleep;

    /**
     * Step count
     */
    @ApiModelProperty("运动步数")
    private String step;

    /**
     * Record time
     */
    private Date time;
}
