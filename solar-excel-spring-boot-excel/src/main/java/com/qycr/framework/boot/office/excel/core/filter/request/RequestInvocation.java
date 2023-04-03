package com.qycr.framework.boot.office.excel.core.filter.request;

import com.qycr.framework.boot.office.excel.core.filter.AbstractInvocation;
import com.qycr.framework.boot.office.excel.core.filter.Invocation;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestInvocation extends AbstractInvocation implements Invocation {


    public RequestInvocation(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        super(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
    }
}
