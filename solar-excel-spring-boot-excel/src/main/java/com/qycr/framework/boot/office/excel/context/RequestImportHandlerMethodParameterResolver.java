package com.qycr.framework.boot.office.excel.context;

import com.qycr.framework.boot.office.excel.annotation.RequestImport;
import com.qycr.framework.boot.office.excel.core.filter.DefaultResolverFilterChain;
import com.qycr.framework.boot.office.excel.core.filter.request.RequestInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestImportHandlerMethodParameterResolver  extends AbstractHandlerMethodParameterResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestImport.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return new DefaultResolverFilterChain(getResolverFilters()).doFilter(new RequestInvocation(parameter,mavContainer,webRequest,binderFactory)).getResult();
    }
}
