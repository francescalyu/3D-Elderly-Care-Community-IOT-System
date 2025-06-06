package com.francesca.constant;

/**
 * @author francesca
 *
 * 2025-05-15
 *
 */
public class UrlConstant {

    public static final String iot_base = "/api/v1";

    /**
     * dashboard
     */
    public static final String Dashboard = iot_base + "/dash";

        /**
         * dashboard - power
         */
        public static final String Dashboard_Power = Dashboard + "/power";

        /**
         * dashboard - power
         */
        public static final String Dashboard_Air = Dashboard + "/air";


    /**
     * subsystem
     */
    public static final String Subsys = iot_base + "/subsys";

    /**
     * area
     */
    public static final String Area = iot_base + "/area";

    /**
     * product
     */
    public static final String Product = iot_base + "/product";


    /**
     * device
     */
    public static final String Device = iot_base + "/device";

    /**
     * point
     */
    public static final String Point = iot_base + "/point";

    /**
     * warn
     */
    public static final String Warn = iot_base + "/warn";

    /**
     * warn rule
     */
    public static final String WarnRule = Warn + "/rule";

}
