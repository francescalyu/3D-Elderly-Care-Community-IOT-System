package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @Author francesca

 * 2025-07-01
 */
@Data
public  class UStone3WaySwitch {

    @JsonProperty("power1")
    public int power1;

    @JsonProperty("power2")
    public int power2;

    @JsonProperty("power3")
    public int power3;

    @JsonProperty("voltage_rms")
    public String voltageRms;

    @JsonProperty("current_rms")
    public String currentRms;

    @JsonProperty("active_power")
    public String activePower;

    @JsonProperty("apparent_power")
    public String appareantPower;

    @JsonProperty("power_factor")
    public String powerFactor;

    @JsonProperty("energy_today")
    public String energyToday;

    @JsonProperty("energy_yesterday")
    public String energyYesterday;

    @JsonProperty("date_time")
    public String dateTime;

    private BigDecimal count1minEnergy;

    public void countMinEnergyWhen0(){

        BigDecimal energy = new BigDecimal(energyToday);

        //处理时钟有问题的开关, 计算1分钟电量
        if (energy.compareTo(BigDecimal.ZERO) == 0){
            BigDecimal aPower = new BigDecimal(activePower);

            // 1min = 1/60 hours
            BigDecimal oneMin = new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP);

            BigDecimal energy1min = aPower.multiply(oneMin);

            // wh /1000 = kwh
            energy1min = energy1min.divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP);

            this.setCount1minEnergy(energy1min);
            this.setEnergyToday(String.valueOf(energy1min));


        }else {
            this.setCount1minEnergy(energy);
        }

    }

}
