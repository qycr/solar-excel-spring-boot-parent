package com.qycr.framework.boot.office.excel.core.support.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatedProvider {

    <T> Set<ConstraintViolation<T>> validate(T obj, Class<?>... groups);

    <T> T unwrap(Class<T> type);

}
