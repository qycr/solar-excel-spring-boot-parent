package com.qycr.framework.boot.office.excel.core.parser;


import com.qycr.framework.boot.office.excel.core.filter.Invocation;
import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;

public interface ExcelResolver {
    ResultCommand resolver(Invocation invocation) throws Exception;


}
