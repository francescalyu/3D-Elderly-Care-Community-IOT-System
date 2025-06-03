package com.francesca.util;

/**
 * Common Util
 *
 * @author francesca
 *
 * 25/6/3
 *
 */


public class CommonUtil {


    public static String toNumberStr(String in) {

        return in.replaceAll("[^0-9.]", "").trim();

    }

}
