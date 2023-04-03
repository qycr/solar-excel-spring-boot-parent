package com.qycr.framework.boot.office.excel.core.filter;

public interface ResultCommand{

     <T> T  getResult();

     Throwable throwable();

}
