package com.qycr.framework.boot.office.excel.core.filter;

import org.springframework.core.AttributeAccessorSupport;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public  abstract class AbstractInvocation extends AttributeAccessorSupport implements Invocation{

    private final transient MethodParameter methodParameter;

    private final transient ModelAndViewContainer modelAndViewContainer;

    private final transient NativeWebRequest nativeWebRequest;

    private final transient WebDataBinderFactory webDataBinderFactory;

    private static final ConversionService conversionService = new FormattingConversionService();


    protected AbstractInvocation(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        this.methodParameter = methodParameter;
        this.modelAndViewContainer = modelAndViewContainer;
        this.nativeWebRequest = nativeWebRequest;
        this.webDataBinderFactory = webDataBinderFactory;
    }

    @Override
    public MethodParameter parameter() {
        return this.methodParameter;
    }

    @Override
    public ModelAndViewContainer mavContainer() {
        return this.modelAndViewContainer;
    }

    @Override
    public NativeWebRequest webRequest() {
        return this.nativeWebRequest;
    }

    @Override
    public WebDataBinderFactory binderFactory() {
        return this.webDataBinderFactory;
    }


    @Override
    public <T> T getProperty(String propertyName, Class<T> type) {
        Object attribute = getAttribute(propertyName);
        if (attribute != null) {
            return conversionService.convert(attribute,type);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractInvocation that = (AbstractInvocation) o;
        return Objects.equals(methodParameter, that.methodParameter) && Objects.equals(modelAndViewContainer, that.modelAndViewContainer) && Objects.equals(nativeWebRequest, that.nativeWebRequest) && Objects.equals(webDataBinderFactory, that.webDataBinderFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
    }
}
