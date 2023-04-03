package com.qycr.framework.boot.office.excel.core.filter.request;

import com.qycr.framework.boot.office.excel.core.exception.SolarLimiterException;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.parser.SimpleEasyExcelResolver;
import com.qycr.framework.boot.office.excel.core.support.RequestImportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;

public class RequestInvocationResolverFilter implements ResolverFilter {


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {

        RequestImportDefinition requestImportDefinition = invocation.getProperty(SolarExcelAttribute.REQUEST_IMPORT_DEFINITION, RequestImportDefinition.class);

        Constructor<SimpleEasyExcelResolver> simpleEasyExcelResolverConstructor =
                                 ReflectionUtils.accessibleConstructor(SimpleEasyExcelResolver.class, Integer.class, Integer.class);
        SimpleEasyExcelResolver simpleEasyExcelResolver = BeanUtils.instantiateClass(simpleEasyExcelResolverConstructor,
                     requestImportDefinition.getLimitLineNumber(), requestImportDefinition.getHeadRowNumber());
        ResultCommand resultCommand = simpleEasyExcelResolver.resolver(invocation);

        if (resultCommand.throwable() != null) {
            throw new SolarLimiterException(resultCommand.throwable().getMessage());
        }
        return resultCommand;
    }

    @Override
    public FilterType filterType() {
        return FilterType.REQUEST;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
