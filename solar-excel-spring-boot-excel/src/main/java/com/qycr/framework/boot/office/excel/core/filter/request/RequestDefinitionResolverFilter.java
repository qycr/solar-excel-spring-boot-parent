package com.qycr.framework.boot.office.excel.core.filter.request;

import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.support.PredicateConsumer;
import com.qycr.framework.boot.office.excel.core.support.RequestImportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpInputMessage;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public class RequestDefinitionResolverFilter implements ResolverFilter {


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {
        RequestImportDefinition requestImportDefinition = invocation.getProperty(SolarExcelAttribute.REQUEST_IMPORT_DEFINITION, RequestImportDefinition.class);

        ExcelReaderBuilder readerBuilder = invocation.getProperty(SolarExcelAttribute.EXCEL_READER_BUILDER, ExcelReaderBuilder.class);

        PredicateConsumer.getInstance()
                .consumerSupplier(readerBuilder::ignoreEmptyRow, requestImportDefinition::isIgnoreEmptyRow)

                .consumer(readerBuilder::autoCloseStream, Boolean.TRUE)
                .consumerSupplier(readerBuilder::excelType, requestImportDefinition.getHttpInputMessage(), this::buildType)
                .consumerSupplier(readerBuilder::password, requestImportDefinition::getPassword)
                .consumer(readerBuilder::autoTrim, Boolean.TRUE)
                .teConsumer(this::consumer,readerBuilder,requestImportDefinition.getHeads(),invocation.parameter());



        return chain.doFilter(invocation);
    }


    private Class<?> headType(ResolvableType resolvableType) {
        if (resolvableType.isAssignableFrom(List.class)) {
            throw new UnsupportedOperationException();
        }
        return resolvableType.resolveGeneric(0);
    }


    private void consumer(ExcelReaderBuilder readerBuilder, List<List<String>> heads, MethodParameter methodParameter) {

        if (!CollectionUtils.isEmpty(heads)) {
            readerBuilder.head(heads);
        } else {
            readerBuilder.head(headType(ResolvableType.forMethodParameter(methodParameter)));
        }
    }


    private ExcelTypeEnum buildType(HttpInputMessage httpInputMessage) {

        ContentDisposition contentDisposition = httpInputMessage.getHeaders().getContentDisposition();
        String fileName = contentDisposition.getFilename();

        if (StringUtils.isEmpty(fileName)) {
            return ExcelTypeEnum.XLSX;
        }
        if (fileName.endsWith(ExcelTypeEnum.XLSX.getValue())) {
            return ExcelTypeEnum.XLSX;
        } else if (fileName.endsWith(ExcelTypeEnum.XLS.getValue())) {
            return ExcelTypeEnum.XLS;
        } else if (fileName.endsWith(ExcelTypeEnum.CSV.getValue())) {
            return ExcelTypeEnum.CSV;
        }
        return ExcelTypeEnum.XLSX;
    }

    @Override
    public FilterType filterType() {
        return FilterType.REQUEST;
    }

    @Override
    public int getOrder() {
        return -800;
    }
}
