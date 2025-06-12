package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MultiButtonEvent extends BaseEvent {
    @JsonProperty("button1")
    private String button1;
    @JsonProperty("button2")
    private String button2;
    @JsonProperty("button3")
    private String button3;

    @Override
    public String getEventType() {
        return "multiButton";
    }

    // getters and setters
}
