package com.qycr.framework.boot.office.excel.annotation;

import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.core.parser.ExcelResolver;
import com.qycr.framework.boot.office.excel.core.parser.SimpleEasyExcelResolver;
import com.qycr.framework.boot.office.excel.core.support.RepositoryProvider;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestImport {

    @AliasFor("name")
    String value() default "";


    @AliasFor("value")
    String name() default "";


    boolean required() default true;


    int headRowNumber() default 1;

    String password() default "";

    boolean ignoreEmptyRow() default false;


    Class<? extends RepositoryProvider> provider() default RepositoryProvider.class;


    Class<? extends ReadListener> configurationListener() default ReadListener.class;


    String dataBinderName() default "";


    int limitLineNumber() default 10000;


    Class<? extends ExcelResolver> resolver() default SimpleEasyExcelResolver.class;

    Filter includeFilters() default @Filter;

    Filter excludeFilters() default @Filter;

    Sheet[] sheets() default {@Sheet};

}
