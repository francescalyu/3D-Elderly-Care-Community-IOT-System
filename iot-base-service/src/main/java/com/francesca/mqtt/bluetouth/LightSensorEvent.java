package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LightSensorEvent extends BaseEvent {
    @JsonProperty("illuminance")
    private String illuminance;
    @JsonProperty("temperature")
    private String temperature;
    @JsonProperty("humidity")
    private String humidity;

    @Override
    public String getEventType() {
        return "lightSensor";
    }

    // getters and setters
}
