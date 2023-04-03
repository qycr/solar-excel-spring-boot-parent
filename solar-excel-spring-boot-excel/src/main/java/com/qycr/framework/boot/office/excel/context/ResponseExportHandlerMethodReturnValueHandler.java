package com.qycr.framework.boot.office.excel.context;

import com.qycr.framework.boot.office.excel.annotation.ResponseExport;
import com.qycr.framework.boot.office.excel.core.filter.DefaultResolverFilterChain;
import com.qycr.framework.boot.office.excel.core.filter.response.ResponseInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ResponseExportHandlerMethodReturnValueHandler extends AbstractHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponseExport.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        new DefaultResolverFilterChain(getResolverFilters()).doFilter(new ResponseInvocation(returnValue,returnType,mavContainer,webRequest,null)).getResult();
    }
}
