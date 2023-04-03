package com.qycr.framework.boot.office.excel.core.support.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public final class SimpleValidatedProvider implements ValidatedProvider{

    private static final Validator validator;

    private static final ValidatorFactory validatedFactory;

    private static final ValidatedProvider  validatedProvider = new SimpleValidatedProvider();

    static {
        validatedFactory = Validation.buildDefaultValidatorFactory();
        validator = validatedFactory.getValidator();
    }


    private SimpleValidatedProvider(){

    }

    public static ValidatedProvider  getInstance(){
        return validatedProvider;
    }


    @Override
    public <T> Set<ConstraintViolation<T>> validate(T obj, Class<?>... groups) {
        return validator.validate(obj,groups);
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return validator.unwrap(type);
    }
}
