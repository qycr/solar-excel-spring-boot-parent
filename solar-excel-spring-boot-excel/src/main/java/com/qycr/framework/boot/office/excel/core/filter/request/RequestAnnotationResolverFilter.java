package com.qycr.framework.boot.office.excel.core.filter.request;

import com.qycr.framework.boot.office.excel.annotation.RequestImport;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.support.RequestImportDefinition;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class RequestAnnotationResolverFilter implements ResolverFilter {

    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {

        final RequestImport requestImport = invocation.parameter().getParameterAnnotation(RequestImport.class);

        Assert.state(requestImport != null, "No requestImport");

        final String requestImportName = getRequestImportName(invocation.parameter(), requestImport);

        final HttpServletRequest request = invocation.webRequest().getNativeRequest(HttpServletRequest.class);
        Assert.state(request != null, "No HttpServletRequest");
        final MultipartRequest multipartRequest = MultipartResolutionDelegate.resolveMultipartRequest(invocation.webRequest());

        HttpInputMessage httpInputMessage = builderInputMessage(multipartRequest, request, requestImportName);

        RequestImportDefinition definition = parserDefinition(requestImport, invocation.parameter(), requestImportName, httpInputMessage);

        invocation.setAttribute(SolarExcelAttribute.REQUEST_IMPORT_DEFINITION,definition);

        return chain.doFilter(invocation);
    }

    @Override
    public FilterType filterType() {
        return FilterType.REQUEST;
    }

    @Override
    public int getOrder() {
        return -1000;
    }

    private String getRequestImportName(MethodParameter methodParam, RequestImport requestImport) {
        String partName = (requestImport != null ? requestImport.name() : "");
        if (partName.isEmpty()) {
            partName = methodParam.getParameterName();
            if (partName == null) {
                throw new IllegalArgumentException("Request part name for argument type [" +
                        methodParam.getNestedParameterType().getName() +
                        "] not specified, and parameter name information not found in class file either.");
            }
        }
        return partName;
    }

    private HttpInputMessage builderInputMessage(final MultipartRequest multipartRequest, final HttpServletRequest request, final String requestImportName) {
        if (Objects.nonNull(multipartRequest)) {
            return new HttpInputMessage() {
                @Override
                public HttpHeaders getHeaders() {
                    return ((MultipartHttpServletRequest) multipartRequest).getMultipartHeaders(requestImportName);
                }

                @Override
                public InputStream getBody() throws IOException {
                    MultipartFile multipartFile = multipartRequest.getFile(requestImportName);
                    Assert.notNull(multipartFile ," must be not null");
                    return multipartFile.getInputStream();
                }
            };

        }
        return new ServletServerHttpRequest(request);
    }

    private RequestImportDefinition parserDefinition(RequestImport requestImport, MethodParameter methodParameter , String requestImportName,HttpInputMessage httpInputMessage) {

        return RequestImportDefinition.builder()

                .resolver(requestImport.resolver())

                .configurationListener(requestImport.configurationListener())

                .headRowNumber(requestImport.headRowNumber())
                .ignoreEmptyRow(requestImport.ignoreEmptyRow())

                .limitLineNumber(requestImport.limitLineNumber())
                .required(requestImport.required())
                .name(requestImportName)
                .httpInputMessage(httpInputMessage)
                .returnType(methodParameter.getParameterType())
                .build();
    }
}
