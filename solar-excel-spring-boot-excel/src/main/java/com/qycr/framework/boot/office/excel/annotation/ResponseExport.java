package com.qycr.framework.boot.office.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.qycr.framework.boot.office.excel.core.support.ExcelHeadProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseExport {


    String fileName() default "";

    String resolverBeanName() default "";

    boolean template() default false;

    String templatePath() default "";


    String password() default "";

    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;

    boolean  inMemory() default false;

    Class<? extends ExcelHeadProvider> head() default ExcelHeadProvider.class;

    Filter includeFilters() default @Filter;

    Filter excludeFilters() default @Filter;

    Sheet[] sheets() default {@Sheet};


}
