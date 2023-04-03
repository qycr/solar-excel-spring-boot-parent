package com.qycr.framework.boot.autoconfigure;

import com.qycr.framework.boot.office.excel.context.RequestAnalyzerMethodParameterResolver;
import com.qycr.framework.boot.office.excel.context.RequestImportHandlerMethodParameterResolver;
import com.qycr.framework.boot.office.excel.context.ResponseExportHandlerMethodReturnValueHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnBean(SolarExcelAutoConfiguration.class)
@AutoConfigureAfter(SolarExcelAutoConfiguration.class)
public class SolarMvcAutoConfiguration implements InitializingBean {

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private RequestAnalyzerMethodParameterResolver requestAnalyzerMethodParameterResolver;

    private RequestImportHandlerMethodParameterResolver requestImportHandlerMethodParameterResolver;

    private ResponseExportHandlerMethodReturnValueHandler responseExportHandlerMethodReturnValueHandler;


    public SolarMvcAutoConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter,
                                     RequestAnalyzerMethodParameterResolver requestAnalyzerMethodParameterResolver,
                                     RequestImportHandlerMethodParameterResolver requestImportHandlerMethodParameterResolver,
                                     ResponseExportHandlerMethodReturnValueHandler responseExportHandlerMethodReturnValueHandler) {

        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.requestAnalyzerMethodParameterResolver = requestAnalyzerMethodParameterResolver;
        this.requestImportHandlerMethodParameterResolver = requestImportHandlerMethodParameterResolver;
        this.responseExportHandlerMethodReturnValueHandler = responseExportHandlerMethodReturnValueHandler;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (requestMappingHandlerAdapter == null) {
            throw new IllegalArgumentException("'requestMappingHandlerAdapter' must be not null");
        }

        if (requestImportHandlerMethodParameterResolver == null) {
            throw new IllegalArgumentException("'requestImportHandlerMethodParameterResolver' must be not null");
        }

        if (responseExportHandlerMethodReturnValueHandler == null) {
            throw new IllegalArgumentException("'responseExportHandlerMethodReturnValueHandler' must be not null");
        }

        final List<HandlerMethodReturnValueHandler> valueHandlers = this.requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        returnValueHandlers.add(responseExportHandlerMethodReturnValueHandler);
        returnValueHandlers.addAll(valueHandlers);
        this.requestMappingHandlerAdapter.setReturnValueHandlers(returnValueHandlers);

        final List<HandlerMethodArgumentResolver> argumentResolvers = this.requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> methodArgumentResolvers = new ArrayList<>();
        methodArgumentResolvers.add(requestImportHandlerMethodParameterResolver);
        methodArgumentResolvers.add(requestAnalyzerMethodParameterResolver);
        methodArgumentResolvers.addAll(argumentResolvers);
        this.requestMappingHandlerAdapter.setArgumentResolvers(methodArgumentResolvers);

    }


}
