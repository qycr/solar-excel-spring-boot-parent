package com.qycr.framework.boot.office.excel.context;

import com.qycr.framework.boot.office.excel.annotation.Analyzer;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestAnalyzerMethodParameterResolver implements HandlerMethodArgumentResolver {


    private final ConversionService conversionService = new FormattingConversionService();


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Analyzer.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Assert.state(mavContainer != null,
                "Errors/BindingResult argument only supported on regular handler methods");

        ModelMap model = mavContainer.getModel();
        String lastKey = CollectionUtils.lastElement(model.keySet());
        if (lastKey != null && lastKey.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
            BindingResult bindingResult = conversionService.convert(model.get(lastKey), BindingResult.class);
            Assert.state(bindingResult != null,
                    "NO bindingResult ");
            return conversionService.convert(bindingResult.getTarget(),parameter.getParameterType());
        }

        throw new IllegalStateException(
                "An Errors/BindingResult argument is expected to be declared the model attribute, the @Analyzer type arguments to which they apply: " + parameter.getMethod());

    }






}
