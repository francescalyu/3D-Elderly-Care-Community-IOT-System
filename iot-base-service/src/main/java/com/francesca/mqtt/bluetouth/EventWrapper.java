package com.francesca.mqtt.bluetouth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import lombok.Data;


public class EventWrapper {
    @JsonProperty("event")
    private BaseEvent event;

    public BaseEvent getEvent() { return event; }
    public void setEvent(BaseEvent event) { this.event = event; }

}
