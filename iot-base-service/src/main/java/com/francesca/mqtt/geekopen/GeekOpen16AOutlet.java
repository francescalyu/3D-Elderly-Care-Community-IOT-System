package com.francesca.mqtt.geekopen;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class GeekOpen16AOutlet {


    @JsonProperty("messageId")
    private String messageId;

    @JsonProperty("mac")
    private String mac;

    @JsonProperty("key")
    private int key;

    @JsonProperty("voltage")
    private String voltage;

    @JsonProperty("current")
    private String current;

    @JsonProperty("power")
    private String power;

    @JsonProperty("energy")
    private BigDecimal energy;

    private BigDecimal energyToday;

    private BigDecimal count1minEnergy;

    public void countMinEnergyWhen0(){

            BigDecimal aPower = new BigDecimal(power);
            // 1min = 1/60 hours
            BigDecimal oneMin = new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP);
            BigDecimal energy1min = aPower.multiply(oneMin);

            // wh /1000 = kwh
            energy1min = energy1min.divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP);

            if(ObjectUtil.isEmpty(this.count1minEnergy)){
                this.count1minEnergy = new BigDecimal(0);
            }

            this.count1minEnergy = this.count1minEnergy.add(energy1min);

            if (ObjectUtil.isEmpty(energyToday)){
                energyToday = new BigDecimal(0);
            }


            energyToday = energyToday.add(energy1min);

    }

}
