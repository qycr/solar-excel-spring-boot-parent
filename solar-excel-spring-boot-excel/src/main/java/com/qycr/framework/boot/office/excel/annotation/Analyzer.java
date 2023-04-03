package com.qycr.framework.boot.office.excel.annotation;


import com.qycr.framework.boot.office.excel.core.support.FailureAnalyzer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * {@link FailureAnalyzer}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Analyzer {


}
