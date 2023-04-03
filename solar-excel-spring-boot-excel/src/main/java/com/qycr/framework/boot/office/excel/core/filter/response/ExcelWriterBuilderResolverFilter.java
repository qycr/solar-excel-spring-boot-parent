package com.qycr.framework.boot.office.excel.core.filter.response;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.support.ExcelWriteBuilderCustomizer;
import com.qycr.framework.boot.office.excel.core.support.ResponseExportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelWriterBuilderResolverFilter implements ResolverFilter, EmbeddedValueResolverAware {


    private StringValueResolver stringValueResolver;

    private List<ExcelWriteBuilderCustomizer> customizers;

    public ExcelWriterBuilderResolverFilter(ObjectProvider<ExcelWriteBuilderCustomizer> objectProvider) {
        customizers = objectProvider.stream().collect(Collectors.toList());
    }


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {

        ExcelWriterBuilder excelWriterBuilder = invocation.getProperty(SolarExcelAttribute.EXCEL_WRITE_BUILDER, ExcelWriterBuilder.class);
        ResponseExportDefinition responseExportDefinition = invocation.getProperty(SolarExcelAttribute.RESPONSE_EXPORT_DEFINITION, ResponseExportDefinition.class);

        if (excelWriterBuilder == null) {
            excelWriterBuilder = EasyExcelFactory.write();
            invocation.setAttribute(SolarExcelAttribute.EXCEL_WRITE_BUILDER, excelWriterBuilder);
        }


        if (!CollectionUtils.isEmpty(customizers)) {
            for (ExcelWriteBuilderCustomizer customizer : customizers) {
                customizer.customizer(excelWriterBuilder);
            }
        }


        String fileName = String.format("%s%s", URLEncoder.encode(responseExportDefinition.getFileName(), "UTF-8"), responseExportDefinition.getSuffix().getValue());
        String contentType = MediaTypeFactory.getMediaType(fileName).map(MediaType::toString)
                .orElse("application/vnd.ms-excel");

        responseExportDefinition.getHttpServletResponse().setContentType(contentType);
        responseExportDefinition.getHttpServletResponse().setCharacterEncoding("utf-8");
        responseExportDefinition.getHttpServletResponse().setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        excelWriterBuilder.file(responseExportDefinition.getHttpServletResponse().getOutputStream())

                 .autoCloseStream(true).autoTrim(true)
                 .excelType(responseExportDefinition.getSuffix())
                 .inMemory(responseExportDefinition.isInMemory())
                 .head(headType(invocation.parameter()));

        if (StringUtils.hasText(responseExportDefinition.getPassword())) {
            excelWriterBuilder.password(responseExportDefinition.getPassword());
        }
        if (responseExportDefinition.isTemplate()) {
            ClassPathResource classPathResource = new ClassPathResource(stringValueResolver.resolveStringValue(responseExportDefinition.getTemplatePath()));
            excelWriterBuilder.withTemplate(classPathResource.getInputStream());
        }
        return chain.doFilter(invocation);
    }

    private Class<?> headType(MethodParameter methodParameter) {
        ResolvableType resolvableType = ResolvableType.forMethodParameter(methodParameter);
        if (resolvableType.isAssignableFrom(List.class)) {
            throw new UnsupportedOperationException();
        }
        return resolvableType.resolveGeneric(0);
    }

    @Override
    public FilterType filterType() {
        return FilterType.RESPONSE;
    }

    @Override
    public int getOrder() {
        return -900;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
