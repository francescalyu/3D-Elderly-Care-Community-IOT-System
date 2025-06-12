package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HealthBandEvent extends BaseEvent {
    @JsonProperty("name")
    private String name;
    @JsonProperty("did")
    private String did;
    @JsonProperty("heartRate")
    private String heartRate;
    @JsonProperty("bloodPressure")
    private String bloodPressure;
    @JsonProperty("notWearingAlert")
    private String notWearingAlert;
    @JsonProperty("sosAlert")
    private String sosAlert;
    @JsonProperty("fallingAlert")
    private String fallingAlert;
    @JsonProperty("bodyTemperature")
    private String bodyTemperature;
    @JsonProperty("totalCalories")
    private String totalCalories;
    @JsonProperty("totalSleep")
    private String totalSleep;
    @JsonProperty("totalSteps")
    private String totalSteps;

    @Override
    public String getEventType() {
        return "healthBand";
    }

    // getters and setters
}
