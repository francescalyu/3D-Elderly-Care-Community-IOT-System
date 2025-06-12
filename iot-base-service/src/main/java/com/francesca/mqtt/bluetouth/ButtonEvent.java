package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ButtonEvent extends BaseEvent {
    @JsonProperty("button2")
    private String button2;

    @Override
    public String getEventType() {
        return "button";
    }

    // getters and setters
}
