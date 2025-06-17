package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HealthBandEvent extends BaseEvent {
    @JsonProperty("name")
    private String name;
    @JsonProperty("did")
    private String did;
    @JsonProperty("heart_rate")
    private String heart_rate;
    @JsonProperty("blood_pressure")
    private String blood_pressure;
    @JsonProperty("not_wearing_alert")
    private String not_wearing_alert;
    @JsonProperty("sos_alert")
    private String sos_alert;
    @JsonProperty("falling_alert")
    private String falling_alert;
    @JsonProperty("body_temperature")
    private String body_temperature;
    @JsonProperty("total_calories")
    private String total_calories;
    @JsonProperty("total_sleep")
    private String total_sleep;
    @JsonProperty("total_steps")
    private String total_steps;

    @Override
    public String getEventType() {
        return "healthBand";
    }

    // getters and setters
}
