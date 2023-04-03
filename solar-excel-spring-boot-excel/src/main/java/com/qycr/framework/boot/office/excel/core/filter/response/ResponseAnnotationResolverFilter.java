package com.qycr.framework.boot.office.excel.core.filter.response;

import com.qycr.framework.boot.office.excel.annotation.ResponseExport;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.support.ResponseExportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;

public class ResponseAnnotationResolverFilter implements ResolverFilter {


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws  Exception {
        invocation.mavContainer().setRequestHandled(true);

        final ResponseExport responseExport = invocation.parameter().getMethodAnnotation(ResponseExport.class);

        Assert.notNull(responseExport , "'responseExport' must be not null");

        ResponseExportDefinition responseExportDefinition = ResponseExportDefinition.builder()

                          .returnType(invocation.parameter()
                          .getParameterType())
                          .fileName(responseExport.fileName())
                          .head(responseExport.head())
                          .inMemory(responseExport.inMemory())

                          .resolverBeanName(responseExport.resolverBeanName())
                          .password(responseExport.password())

                          .suffix(responseExport.suffix())
                          .template(responseExport.template())
                          .templatePath(responseExport.templatePath())

                          .httpServletResponse(invocation.webRequest().getNativeResponse(HttpServletResponse.class))
                          .excludeFilters(responseExport.excludeFilters().value())
                          .includeFilters(responseExport.excludeFilters().value())
                          .build();
        invocation.setAttribute(SolarExcelAttribute.RESPONSE_EXPORT_DEFINITION,responseExportDefinition);
        return chain.doFilter(invocation);
    }

    @Override
    public FilterType filterType() {
        return FilterType.RESPONSE;
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
