package com.francesca.common;

/**
 * 自定义异常
 *
 * @author francesca
 *
 * 2025-05-16
 *
 */
public class CommException extends RuntimeException{

    private static final long seriafrancescaersionUID = 1L;

    private final int errorCode;

    public CommException(){
        super();
        this.errorCode = 500;
    }

    public CommException(final String message) {
        super(message);
        this.errorCode =  500;
    }
    public CommException(int errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
