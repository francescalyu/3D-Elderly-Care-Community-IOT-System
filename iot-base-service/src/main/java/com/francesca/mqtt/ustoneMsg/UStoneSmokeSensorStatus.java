package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @Author francesca

 * 2025-05-29
 */

public class UStoneSmokeSensorStatus {
    @JsonProperty("lpg_ppm")
    private double lpgPpm;

    @JsonProperty("co_ppm")
    private double coPpm;

    @JsonProperty("smoke_ppm")
    private double smokePpm;

    @JsonProperty("mq2_state")
    private String mq2State;

    @JsonProperty("date_time")
    private String dateTime;

    private Long timestamp ;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    // Getters and Setters
    public double getLpgPpm() { return lpgPpm; }
    public void setLpgPpm(double lpgPpm) { this.lpgPpm = lpgPpm; }

    public double getCoPpm() { return coPpm; }
    public void setCoPpm(double coPpm) { this.coPpm = coPpm; }

    public double getSmokePpm() { return smokePpm; }
    public void setSmokePpm(double smokePpm) { this.smokePpm = smokePpm; }

    public String getMq2State() { return mq2State; }
    public void setMq2State(String mq2State) { this.mq2State = mq2State; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
}

