package com.qycr.framework.boot.office.excel.core.support;

public interface FailureAnalyzer {

    int  line();

    int  column();

    String message();

    Throwable throwable();

    <T> T rawData();

}
