package com.qycr.framework.boot.office.excel.core.filter.response;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.support.ResponseExportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SimpleResultCommand;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collection;

public class ResponseInvocationResolverFilter implements ResolverFilter {


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {
        ResponseExportDefinition responseExportDefinition = invocation.getProperty(SolarExcelAttribute.RESPONSE_EXPORT_DEFINITION, ResponseExportDefinition.class);
        ExcelWriterBuilder excelWriterBuilder = invocation.getProperty(SolarExcelAttribute.EXCEL_WRITE_BUILDER, ExcelWriterBuilder.class);
        final WriteSheet sheet = EasyExcelFactory.writerSheet(0, responseExportDefinition.getFileName()).build();
        Object result = ((ResponseInvocation) invocation).getResult();
        ExcelWriter excelWriter = excelWriterBuilder.build();
        excelWriter.write(extract(result,invocation.parameter()), sheet);
        excelWriter.finish();
        return new SimpleResultCommand<>();
    }

    private Collection extract(Object returnValue , MethodParameter parameter){

        if (BeanUtils.isSimpleProperty(parameter.getParameterType())) {
            throw new UnsupportedOperationException(" class with a return value of simple type is not supported");
        }

        if (ClassUtils.isPrimitiveWrapper(parameter.getParameterType()) || ClassUtils.isPrimitiveArray(parameter.getParameterType()) || ClassUtils.isPrimitiveWrapperArray(parameter.getParameterType())){
            throw new UnsupportedOperationException("Wrapping a class with a return value of primitive type is not supported");
        }
        if (parameter.getParameterType().equals(String.class) || parameter.getParameterType().equals(String[].class)) {
            throw new UnsupportedOperationException("The String type is not supported");
        }
        if (returnValue instanceof Collection) {
            return (Collection)returnValue;
        }
        return Arrays.asList(returnValue);
    }

    @Override
    public FilterType filterType() {
        return FilterType.RESPONSE;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }


}
