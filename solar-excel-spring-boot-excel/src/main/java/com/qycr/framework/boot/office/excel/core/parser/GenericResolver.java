package com.qycr.framework.boot.office.excel.core.parser;

import com.qycr.framework.boot.office.excel.core.filter.Invocation;
import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;
import com.qycr.framework.boot.office.excel.core.support.FailureAnalyzer;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface GenericResolver<R> extends ExcelResolver{

   void genericParameter(Invocation invocation)throws Exception;

    R getResult();

    BindingResult bindingResult();

    List<FailureAnalyzer> getFailureAnalyzers();

    @Override
    ResultCommand resolver(Invocation invocation) throws Exception;
}