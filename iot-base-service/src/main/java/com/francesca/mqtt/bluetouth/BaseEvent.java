package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = HealthBandEvent.class, name = "healthBand"),
//        @JsonSubTypes.Type(value = ButtonEvent.class, name = "button"),
//        @JsonSubTypes.Type(value = DoorSensorEvent.class, name = "doorSensor"),
//        @JsonSubTypes.Type(value = MultiButtonEvent.class, name = "multiButton"),
//        @JsonSubTypes.Type(value = LightSensorEvent.class, name = "lightSensor")
//})
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public abstract class BaseEvent {
    @JsonProperty("mac")
    private String mac;
    @JsonProperty("rssi")
    private String rssi;
    @JsonProperty("gid")
    private String gid;
    @JsonProperty("batteryPercentage")
    private String batteryPercentage;

    public abstract String getEventType();
}

