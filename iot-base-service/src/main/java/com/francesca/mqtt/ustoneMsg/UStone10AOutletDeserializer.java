package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author francesca

 * 2025-05-29
 */

public class UStone10AOutletDeserializer extends StdDeserializer<UStone10AOutlet> {

    public UStone10AOutletDeserializer() {
        this(null);
    }

    public UStone10AOutletDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UStone10AOutlet deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        UStone10AOutlet uStone10AOutlet = new UStone10AOutlet();

        // 解析基本字段
        uStone10AOutlet.setVoltageRms(node.path("voltage_rms").asText());
        uStone10AOutlet.setCurrentRms(node.path("current_rms").asText());
        uStone10AOutlet.setActivePower(node.path("active_power").asText());
        uStone10AOutlet.setApparentPower(node.path("apparent_power").asText());
        uStone10AOutlet.setPowerFactor(node.path("power_factor").asText());
        uStone10AOutlet.setEnergyToday(node.path("energy_today").asText());
        uStone10AOutlet.setEnergyYesterday(node.path("energy_yesterday").asText());
        uStone10AOutlet.setDateTime(LocalDateTime.parse(node.path("date_time").asText()));
        uStone10AOutlet.setPower1(node.path("power1").asInt());

        // 解析ir1-ir64字段
        int[] irValues = new int[64];
        for (int i = 1; i <= 64; i++) {
            irValues[i-1] = node.path("ir" + i).asInt();
        }
        uStone10AOutlet.setIrValues(irValues);

        return uStone10AOutlet;
    }
}
