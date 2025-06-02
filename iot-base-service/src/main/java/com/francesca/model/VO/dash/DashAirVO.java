package com.francesca.model.VO.dash;

/**
 * @Author francesca

 * 2025-05-16
 */

import com.francesca.constant.StandardConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@ApiModel("dashboard - air")
public class DashAirVO {

    @ApiModelProperty("时间" )
    private String timestamp;

    @ApiModelProperty("pm2.5" )
    private String pm25;

    @ApiModelProperty("pm2.5 单位" )
    private String pm25Unit;

    @ApiModelProperty("pm10" )
    private String pm10;

    @ApiModelProperty("pm10 单位" )
    private String pm10Unit;

    @ApiModelProperty("总挥发性有机化合物" )
    private String tvoc;

    @ApiModelProperty("总挥发性有机化合物 单位" )
    private String tvocUnit;

    @ApiModelProperty("平均颗粒物尺寸" )
    private String ps;

    @ApiModelProperty("平均颗粒物尺寸单位" )
    private String psUnit;

    @ApiModelProperty("二氧化碳" )
    private String co2;

    @ApiModelProperty("二氧化碳单位" )
    private String co2Unit;

    @ApiModelProperty("温度 " )
    private String temp;

    @ApiModelProperty("温度单位  默认摄氏度" )
    private String tempUnit;

    @ApiModelProperty("湿度   " )
    private String humidity;

    @ApiModelProperty("湿度单位  默认%" )
    private String humidityUnit;

    @ApiModelProperty("室内空气质量  0 - 正常  1 - 轻度   2 - 严重  " )
    private String level;

    @ApiModelProperty("温湿度  0 - 正常  1 - 高温   2 - 低温  3 - 潮湿  4 - 干燥    " )
    private String tempLevel;


    private  void setLevel(){

        level = "0";

        BigDecimal pm25 = new BigDecimal(this.pm25);

        if (pm25.compareTo(StandardConstant.PM25_ug) > 0 ){
            level = "1";
        }

        if (pm25.compareTo(StandardConstant.PM25_ug_danger) > 0 ){
            level = "2";
        }

        BigDecimal pm10 = new BigDecimal(this.pm10);

        if (pm10.compareTo(StandardConstant.PM10_ug) > 0 ){
            level = "1";
        }

        if (pm10.compareTo(StandardConstant.PM10_ug_danger) > 0 ){
            level = "2";
        }

        BigDecimal tvoc = new BigDecimal(this.tvoc);

        if (tvoc.compareTo(StandardConstant.TVOC_ppb) > 0 ){
            level = "2";
        }

        BigDecimal co2 = new BigDecimal(this.co2);

        if (co2.compareTo(StandardConstant.CO2_ppm) > 0 ){
            level = "2";
        }

        tempLevel = "0";

        BigDecimal temp = new BigDecimal( this.temp);
        BigDecimal humd = new BigDecimal( this.humidity);

        if (humd.compareTo(StandardConstant.humidity_wet) > 0 ){
            tempLevel = "3";
        }

        if (humd.compareTo(StandardConstant.humidity_dry) < 0 ){
            tempLevel = "4";
        }

        if (temp.compareTo(StandardConstant.temp_hot) > 0 ){
            tempLevel = "1";
        }

        if (temp.compareTo(StandardConstant.temp_cold) < 0 ){
            tempLevel = "2";
        }

    }

}
