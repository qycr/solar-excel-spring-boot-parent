package com.qycr.framework.boot.office.excel.core.filter;

import org.springframework.core.AttributeAccessor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public interface Invocation extends AttributeAccessor {

    MethodParameter parameter();

    ModelAndViewContainer mavContainer();

    NativeWebRequest webRequest();

    WebDataBinderFactory binderFactory();

    <T> T getProperty(String propertyName ,Class<T> type);


}
