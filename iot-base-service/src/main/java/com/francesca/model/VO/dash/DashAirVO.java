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
@ApiModel("仪表板 - 空气质量")
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

    @ApiModelProperty("平均颗粒物尺寸 单位" )
    private String psUnit;

    @ApiModelProperty("二氧化碳" )
    private String co2;

    @ApiModelProperty("二氧化碳单位" )
    private String co2Unit;

    @ApiModelProperty("温度 " )
    private String temp;

    @ApiModelProperty("温度 单位 " )
    private String tempUnit;

    @ApiModelProperty("湿度   " )
    private String humidity;

    @ApiModelProperty("湿度  单位 " )
    private String humidityUnit;

    @ApiModelProperty("室内空气质量  0 - 正常  1 - 轻度   2 - 严重  " )
    private String level;

    @ApiModelProperty("温湿度  0 - 正常  1 - 高温   2 - 低温  3 - 潮湿  4 - 干燥    " )
    private String tempLevel;

    @ApiModelProperty(" 液化气  单位PPM " )
    private String lpg;

    @ApiModelProperty(" 液化气  0-正常  1-告警  2-严重 " )
    private String lpgLevel;


    @ApiModelProperty(" 一氧化碳  单位PPM " )
    private String co;

    @ApiModelProperty(" 一氧化碳   0-正常  1-告警  2-严重 " )
    private String coLevel;


    @ApiModelProperty("烟雾  单位PPM " )
    private String smoke;

    @ApiModelProperty(" 烟雾  0-正常  1-告警  2-严重 " )
    private String smokeLevel ;



    public   void setLevel(){

        level = "0";


        BigDecimal pm25 = new BigDecimal(this.pm25);

        if (pm25.compareTo(StandardConstant.PM25_ug) > 0 ){
            level = "1";
        }

        if (pm25.compareTo(StandardConstant.PM25_ug_danger) > 0 ){
            level = "2";
        }


        String pm10str = this.pm10.replaceAll("[^0-9.]", "").trim();
        BigDecimal pm10 = new BigDecimal(pm10str);

        if (pm10.compareTo(StandardConstant.PM10_ug) > 0 ){
            level = "1";
        }

        if (pm10.compareTo(StandardConstant.PM10_ug_danger) > 0 ){
            level = "2";
        }

        String tvocstr = this.tvoc.replaceAll("[^0-9.]", "").trim();
        BigDecimal tvoc = new BigDecimal(tvocstr);

        if (tvoc.compareTo(StandardConstant.TVOC_ppb) > 0 ){
            level = "2";
        }

        String co2str = this.co2.replaceAll("[^0-9.]", "").trim();
        BigDecimal co2 = new BigDecimal(co2str);

        if (co2.compareTo(StandardConstant.CO2_ppm) > 0 ){
            level = "2";
        }

        tempLevel = "0";

        String tempstr = this.temp.replaceAll("[^0-9.]", "").trim();
        BigDecimal temp = new BigDecimal( tempstr);
        String humdstr = this.humidity.replaceAll("[^0-9.]", "").trim();
        BigDecimal humd = new BigDecimal( humdstr);

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
