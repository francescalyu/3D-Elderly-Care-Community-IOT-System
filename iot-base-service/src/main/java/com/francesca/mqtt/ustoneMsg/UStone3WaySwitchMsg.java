package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @Author francesca

 * 2025-07-01
 */

public class UStone3WaySwitchMsg {
    private UStone3WaySwitch status;

    @JsonProperty("status")
    public UStone3WaySwitch getStatus() { return status; }
    public void setStatus(UStone3WaySwitch status) { this.status = status; }
}
