package com.francesca.constant;

import java.math.BigDecimal;

/**
 * @author francesca
 *
 * 2025-05-29
 *
 * Standard para define
 *
 * the air standard base on PRC National Ambient air quality standards GB 3095—2012 and US EPA
 *
 */
public class StandardConstant {

    public static final BigDecimal PM25_ug = new BigDecimal(10);

    public static final BigDecimal PM10_ug = new BigDecimal(15);

    public static final BigDecimal PM25_ug_danger = new BigDecimal(50);

    public static final BigDecimal PM10_ug_danger = new BigDecimal(70);

    //the TVOC <100 PPB base on  US EPA
    public static final BigDecimal TVOC_ppb = new BigDecimal(100);

    public static final BigDecimal CO2_ppm = new BigDecimal(1000);

    public static final BigDecimal humidity_dry = new BigDecimal(30);

    public static final BigDecimal humidity_wet = new BigDecimal( 70);

    public static final BigDecimal temp_cold = new BigDecimal(20);

    public static final BigDecimal temp_hot = new BigDecimal(28);

    public static final BigDecimal smoke_warn = new BigDecimal(100);

}
