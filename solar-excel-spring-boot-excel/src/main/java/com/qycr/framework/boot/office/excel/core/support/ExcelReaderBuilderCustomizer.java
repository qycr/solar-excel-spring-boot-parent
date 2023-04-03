package com.qycr.framework.boot.office.excel.core.support;

import com.alibaba.excel.read.builder.ExcelReaderBuilder;

@FunctionalInterface
public interface ExcelReaderBuilderCustomizer {
    void  customizer(ExcelReaderBuilder readerBuilder);

}
