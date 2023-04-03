package com.qycr.framework.boot.office.excel.core.support;


import com.alibaba.excel.write.builder.ExcelWriterBuilder;

@FunctionalInterface
public interface ExcelWriteBuilderCustomizer {

  void  customizer(ExcelWriterBuilder builder);

}
