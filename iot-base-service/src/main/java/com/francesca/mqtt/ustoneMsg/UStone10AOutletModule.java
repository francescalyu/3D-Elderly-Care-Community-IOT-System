package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @Author francesca

 * 2025-05-29
 */

public class UStone10AOutletModule extends SimpleModule {
    public UStone10AOutletModule() {
        addDeserializer(UStone10AOutlet.class, new UStone10AOutletDeserializer());
    }
}
