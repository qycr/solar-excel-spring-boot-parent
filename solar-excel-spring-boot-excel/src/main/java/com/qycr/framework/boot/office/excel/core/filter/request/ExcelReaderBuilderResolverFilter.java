package com.qycr.framework.boot.office.excel.core.filter.request;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.support.ExcelReaderBuilderCustomizer;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ExcelReaderBuilderResolverFilter implements ResolverFilter {


    private List<ExcelReaderBuilderCustomizer> customizers;

    public ExcelReaderBuilderResolverFilter(ObjectProvider<ExcelReaderBuilderCustomizer> objectProvider) {
        customizers = objectProvider.stream().collect(Collectors.toList());
    }


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {
        ExcelReaderBuilder readerBuilder = invocation.getProperty(SolarExcelAttribute.EXCEL_READER_BUILDER, ExcelReaderBuilder.class);
        if (readerBuilder == null) {
            readerBuilder = EasyExcelFactory.read();
            invocation.setAttribute(SolarExcelAttribute.EXCEL_READER_BUILDER, readerBuilder);
        }

        if (!CollectionUtils.isEmpty(customizers)) {
            for (ExcelReaderBuilderCustomizer customizer : customizers) {
                customizer.customizer(readerBuilder);
            }
        }
        return chain.doFilter(invocation);
    }

    @Override
    public FilterType filterType() {
        return FilterType.REQUEST;
    }

    @Override
    public int getOrder() {
        return - 900;
    }

}
