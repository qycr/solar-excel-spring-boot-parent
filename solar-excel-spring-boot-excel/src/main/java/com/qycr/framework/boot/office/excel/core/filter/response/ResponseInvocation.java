package com.qycr.framework.boot.office.excel.core.filter.response;

import com.qycr.framework.boot.office.excel.core.filter.AbstractInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class ResponseInvocation extends AbstractInvocation {


    private  final transient Object result;

    public ResponseInvocation(Object returnValue,MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        super(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        this.result = returnValue;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResponseInvocation that = (ResponseInvocation) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), result);
    }
}
