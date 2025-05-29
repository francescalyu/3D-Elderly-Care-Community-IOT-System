package com.francesca.mqtt.ustoneMsg;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class UStone10AOutletModule extends SimpleModule {
    public UStone10AOutletModule() {
        addDeserializer(UStone10AOutlet.class, new UStone10AOutletDeserializer());
    }
}
