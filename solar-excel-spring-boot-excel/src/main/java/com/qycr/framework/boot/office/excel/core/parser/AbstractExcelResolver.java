package com.qycr.framework.boot.office.excel.core.parser;

import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.core.filter.Invocation;
import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;
import com.qycr.framework.boot.office.excel.core.support.RequestImportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SimpleResultCommand;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;


public  abstract class AbstractExcelResolver<R> extends WebResolver<R> {


    @Override
    public ResultCommand resolver(Invocation invocation) throws Exception {

        ExcelReaderBuilder readerBuilder = invocation.getProperty(SolarExcelAttribute.EXCEL_READER_BUILDER, ExcelReaderBuilder.class);

        RequestImportDefinition importDefinition = invocation.getProperty(SolarExcelAttribute.REQUEST_IMPORT_DEFINITION, RequestImportDefinition.class);

        ExcelReaderBuilder builder = readerBuilder
                 .registerReadListener(getReadListener())
                 .file(importDefinition.getHttpInputMessage().getBody());
        if (importDefinition.isMultiFile()){
            builder.doReadAll();
        }
        else {
            builder.sheet(0).doRead();
        }
        super.genericParameter(invocation);
        return new SimpleResultCommand<>(getResult(),throwable());
    }

    protected abstract Throwable throwable();

    protected abstract ReadListener<R> getReadListener();
}
