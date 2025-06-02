package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * @Author francesca

 * 2025-05-29
 */

@Data
public class UStoneAirSixSensorStatus {
    @JsonProperty("mass% pm1.0")
    private String massPm1;

    @JsonProperty("mass% pm2.5")
    private String massPm2_5;

    @JsonProperty("mass% pm4.0")
    private String massPm4;

    @JsonProperty("mass% pm10")
    private String massPm10;

    @JsonProperty("number% pm0.5")
    private String numberPm0_5;

    @JsonProperty("number% pm1.0")
    private String numberPm1;

    @JsonProperty("number% pm2.5")
    private String numberPm2_5;

    @JsonProperty("number% pm4.0")
    private String numberPm4;

    @JsonProperty("number% pm10")
    private String numberPm10;

    @JsonProperty("particle size")
    private String particleSize;

    @JsonProperty("co2_ppm")
    private String co2Ppm;

    @JsonProperty("tvoc_ppb")
    private String tvocPpb;

    @JsonProperty("chip_temperature")
    private String chipTemperature;

    @JsonProperty("env_temperature")
    private String envTemperature;

    @JsonProperty("env_humidity")
    private String envHumidity;

    private Long timestamp ;

}

