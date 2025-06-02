package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author francesca

 * 2025-05-29
 */

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

    public BigDecimal getCount1minEnergy() {
        return count1minEnergy;
    }

    public void setCount1minEnergy(BigDecimal count1minEnergy) {
        this.count1minEnergy = count1minEnergy;
    }

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private int power1;

    private BigDecimal count1minEnergy;

    private void countMinEnergyWhen0(){

        BigDecimal energy = new BigDecimal(energyToday);

        //处理时钟有问题的10A插座, 计算1分钟电量
        if (energy.equals(0)){
            BigDecimal aPower = new BigDecimal(activePower);

            // 1min = 1/60 hours
            BigDecimal oneMin = new BigDecimal("1").divide(new BigDecimal("60"));

            BigDecimal energy1min = aPower.multiply(oneMin);

            // wh /1000 = kwh
            energy1min = energy1min.divide(new BigDecimal("1000"));

            this.setCount1minEnergy(energy1min);

        }else {
            this.setCount1minEnergy(energy);
        }

    }


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    private Long timestamp ;


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
