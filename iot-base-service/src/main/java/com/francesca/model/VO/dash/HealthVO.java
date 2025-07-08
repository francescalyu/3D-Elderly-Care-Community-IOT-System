package com.francesca.model.VO.dash;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.francesca.mqtt.bluetouth.BaseEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("dashboard - Health")
public class HealthVO {

    @ApiModelProperty(" 心率  单位 bpm " )
    private String heart_rate;

    @ApiModelProperty("参考血压 单位mmHg ")
    private String blood_pressure;

    @ApiModelProperty("手环未佩戴告警  0-正常   1-告警")
    private String not_wearing_alert;


    @ApiModelProperty("摔倒告警 0-正常  1-告警 ")
    private String falling_alert;

    @ApiModelProperty("体温  单位 摄氏度C ")
    private String body_temperature;

    @ApiModelProperty("当日消耗卡路里  单位Cal ")
    private String total_calories;

    @ApiModelProperty("总睡眠时间 单位 minute ")
    private String total_sleep;

    @ApiModelProperty("当日总步数 单位 step ")
    private String total_steps;

    @ApiModelProperty("手环电量  单位% ")
    private String battery_percentage;

    @ApiModelProperty("更新时间  ")
    private String updateTime;

    @ApiModelProperty("在离线  0-离线  1-在线 ")
    private String online;



}
