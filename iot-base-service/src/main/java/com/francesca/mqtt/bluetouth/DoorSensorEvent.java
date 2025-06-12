package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DoorSensorEvent extends BaseEvent {
    @JsonProperty("windoor_open")
    private String windoor_open;

    @Override
    public String getEventType() {
        return "doorSensor";
    }

    // getters and setters
}
