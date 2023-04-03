package com.qycr.framework.boot.office.excel.core.parser;

import com.qycr.framework.boot.office.excel.core.filter.Invocation;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

public abstract class WebResolver<R>  implements GenericResolver<R>{


    private BindingResult bindingResult;

    private static final String GENERIC = "genericResolver";

    public static final String MODEL_KEY_PREFIX = BindingResult.class.getName() + "." + GENERIC;

    @Override
    public void genericParameter(Invocation invocation) throws Exception {
        WebDataBinder dataBinder = invocation.getProperty(SolarExcelAttribute.WEB_DATA_BINDER,WebDataBinder.class);
        if (ObjectUtils.isEmpty(dataBinder)) {
            dataBinder = invocation.binderFactory().createBinder(invocation.webRequest(), this.getFailureAnalyzers(), MODEL_KEY_PREFIX);
        }
        this.bindingResult = dataBinder.getBindingResult();
        invocation.mavContainer().getModel().put(MODEL_KEY_PREFIX, this.bindingResult);
    }

    @Override
    public BindingResult bindingResult() {
        return this.bindingResult;
    }


}
