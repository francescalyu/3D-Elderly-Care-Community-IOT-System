package com.francesca.common;

/**
 *
 * @author francesca
 *
 * 2025-05-16
 */
public class IllegalArgException extends CommException {

    public IllegalArgException(String message) {
        super(400, message);
    }
}
