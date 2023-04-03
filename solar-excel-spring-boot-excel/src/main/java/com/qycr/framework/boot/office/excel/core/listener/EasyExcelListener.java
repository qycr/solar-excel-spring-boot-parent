package com.qycr.framework.boot.office.excel.core.listener;

import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;
import com.qycr.framework.boot.office.excel.core.support.FailureAnalyzer;

import java.util.List;

public interface EasyExcelListener<R> {


    ReadListener<R> getListener();

    ResultCommand getResult();

    List<FailureAnalyzer> getFailureAnalyzer();
}
