package com.qycr.framework.boot.office.excel.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {

    int index() default 0;

    String sheetName() default "";

    int  limiter() default -1;

}
