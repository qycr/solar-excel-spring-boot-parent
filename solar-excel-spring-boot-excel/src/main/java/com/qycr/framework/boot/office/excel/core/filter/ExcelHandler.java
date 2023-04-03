package com.qycr.framework.boot.office.excel.core.filter;

import com.qycr.framework.boot.office.excel.core.filter.Invocation;
import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;


@Deprecated
@FunctionalInterface
public interface ExcelHandler {

     ResultCommand handler(Invocation invocation) throws Exception;

}
