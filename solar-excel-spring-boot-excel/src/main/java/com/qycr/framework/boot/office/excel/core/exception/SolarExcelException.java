package com.qycr.framework.boot.office.excel.core.exception;

public class SolarExcelException extends RuntimeException{


    public SolarExcelException() {
        super();
    }

    public SolarExcelException(String message) {
        super(message);
    }


    public SolarExcelException(String message, Throwable cause) {
        super(message, cause);
    }


    public SolarExcelException(Throwable cause) {
        super(cause);
    }

    protected SolarExcelException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
