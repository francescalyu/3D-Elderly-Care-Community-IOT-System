package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UStone10AOutletMsg {
    private UStone10AOutlet status;

    @JsonProperty("status")
    public UStone10AOutlet getStatus() { return status; }
    public void setStatus(UStone10AOutlet status) { this.status = status; }
}
