package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @Author francesca

 * 2025-05-29
 */

public class UStoneSmokeSensorData {
    @JsonProperty("status")
    private UStoneSmokeSensorStatus status;

    public UStoneSmokeSensorStatus getStatus() {
        return status;
    }

    public void setStatus(UStoneSmokeSensorStatus status) {
        this.status = status;
    }
}
