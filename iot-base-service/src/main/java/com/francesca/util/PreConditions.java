package com.francesca.util;

import com.francesca.common.IllegalArgException;
import org.springframework.util.StringUtils;

/**
 * 前置条件工具类
 *
 * @author francesca
 *
 * 2025-05-15
 *
 */
public class PreConditions {


    public static void assertNotEmpty(String str, String msg) {
        if (!StringUtils.hasText(str)) {
            throw new IllegalArgException(msg);
        }

    }

    public static void assertNotNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalArgException(msg);
        }

    }

    public static void assertArgument(final boolean expression, final String msg) {
        if (!expression) {
            throw new IllegalArgException(msg);
        }
    }
}
