package com.qycr.framework.boot.office.excel.annotation;

import com.qycr.framework.boot.office.excel.core.filter.ResolverFilter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter{

    @AliasFor("classes")
    Class<? extends ResolverFilter>[] value() default {};

    @AliasFor("value")
    Class<? extends ResolverFilter>[] classes() default {};

}