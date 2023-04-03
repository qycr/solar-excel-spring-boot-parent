package com.qycr.framework.boot.office.excel.core.exception;

public class SolarLimiterException extends RuntimeException {


    public SolarLimiterException() {
        super();
    }

    public SolarLimiterException(String message) {
        super(message);
    }


    public SolarLimiterException(String message, Throwable cause) {
        super(message, cause);
    }


    public SolarLimiterException(Throwable cause) {
        super(cause);
    }

    protected SolarLimiterException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}