package com.qycr.framework.boot.autoconfigure;


import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.qycr.framework.boot.office.excel.context.RequestAnalyzerMethodParameterResolver;
import com.qycr.framework.boot.office.excel.context.RequestImportHandlerMethodParameterResolver;
import com.qycr.framework.boot.office.excel.context.ResponseExportHandlerMethodReturnValueHandler;
import com.qycr.framework.boot.office.excel.core.filter.request.*;
import com.qycr.framework.boot.office.excel.core.filter.response.*;
import com.qycr.framework.boot.office.excel.core.support.ExcelReaderBuilderCustomizer;
import com.qycr.framework.boot.office.excel.core.support.ExcelWriteBuilderCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ExcelReaderBuilder.class)
@ConditionalOnProperty(prefix = SolarExcelProperties.SOLAR_EXCEL_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
@EnableConfigurationProperties(SolarExcelProperties.class)
public class SolarExcelAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public RequestAnnotationResolverFilter requestAnnotationResolverFilter() {
        return new RequestAnnotationResolverFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestDefinitionResolverFilter requestDefinitionResolverFilter() {
        return new RequestDefinitionResolverFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExcelReaderBuilderResolverFilter excelReaderBuilderResolverFilter(ObjectProvider<ExcelReaderBuilderCustomizer> objectProvider) {
        return new ExcelReaderBuilderResolverFilter(objectProvider);
    }


    @Bean
    @ConditionalOnMissingBean
    public RequestInvocationResolverFilter invocationResolverFilter() {
        return new RequestInvocationResolverFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggerResolverFilter loggerResolverFilter() {
        return new LoggerResolverFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestImportHandlerMethodParameterResolver requestImportHandlerMethodParameterResolver() {
        return new RequestImportHandlerMethodParameterResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseExportHandlerMethodReturnValueHandler responseExportHandlerMethodReturnValueHandler() {
        return new ResponseExportHandlerMethodReturnValueHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestAnalyzerMethodParameterResolver requestAnalyzerMethodParameterResolver(){
        return new RequestAnalyzerMethodParameterResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseAnnotationResolverFilter responseAnnotationResolverFilter() {
        return new ResponseAnnotationResolverFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public SelectorResolverFilter selectorResolverFilter(SolarExcelProperties solarExcelProperties) {
        return new SelectorResolverFilter(solarExcelProperties.getErrorBox(),solarExcelProperties.getPromptBox());
    }

    @Bean
    @ConditionalOnMissingBean
    public ExcelWriterBuilderResolverFilter excelWriterBuilderResolverFilter(ObjectProvider<ExcelWriteBuilderCustomizer> objectProvider) {
        return new ExcelWriterBuilderResolverFilter(objectProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseInvocationResolverFilter responseInvocationResolverFilter(){
        return new ResponseInvocationResolverFilter();
    }


}
