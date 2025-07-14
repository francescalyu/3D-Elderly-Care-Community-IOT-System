package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String heartrate;

    /**
     * Blood pressure
     */
    private String bloodpressure;

    /**
     * Body temperature
     */
    private String bodytemp;

    /**
     * Calories burned
     */
    private String calories;

    /**
     * Sleep data
     */
    private String sleep;

    /**
     * Step count
     */
    private String step;

    /**
     * Record time
     */
    private Date time;
}
