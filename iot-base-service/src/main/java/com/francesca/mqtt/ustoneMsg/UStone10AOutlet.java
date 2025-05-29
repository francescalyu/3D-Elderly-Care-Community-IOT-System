package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class UStone10AOutlet {
    @JsonProperty("voltage_rms")
    private String voltageRms;

    @JsonProperty("current_rms")
    private String currentRms;

    @JsonProperty("active_power")
    private String activePower;

    @JsonProperty("apparent_power")
    private String apparentPower;

    @JsonProperty("power_factor")
    private String powerFactor;

    @JsonProperty("energy_today")
    private String energyToday;

    @JsonProperty("energy_yesterday")
    private String energyYesterday;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private int power1;


    private int[] irValues = new int[64]; // 索引0对应ir1，索引63对应ir64

    // getters and setters
    public String getVoltageRms() { return voltageRms; }
    public void setVoltageRms(String voltageRms) { this.voltageRms = voltageRms; }

    public String getCurrentRms() { return currentRms; }
    public void setCurrentRms(String currentRms) { this.currentRms = currentRms; }

    public String getActivePower() { return activePower; }
    public void setActivePower(String activePower) { this.activePower = activePower; }

    public String getApparentPower() { return apparentPower; }
    public void setApparentPower(String apparentPower) { this.apparentPower = apparentPower; }

    public String getPowerFactor() { return powerFactor; }
    public void setPowerFactor(String powerFactor) { this.powerFactor = powerFactor; }

    public String getEnergyToday() { return energyToday; }
    public void setEnergyToday(String energyToday) { this.energyToday = energyToday; }

    public String getEnergyYesterday() { return energyYesterday; }
    public void setEnergyYesterday(String energyYesterday) { this.energyYesterday = energyYesterday; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public int getPower1() { return power1; }
    public void setPower1(int power1) { this.power1 = power1; }

    public int[] getIrValues() { return irValues; }
    public void setIrValues(int[] irValues) { this.irValues = irValues; }


    public int getIrValue(int index) {
        if (index >= 1 && index <= 64) {
            return irValues[index - 1];
        }
        throw new IllegalArgumentException("IR index must be between 1 and 64");
    }
}
