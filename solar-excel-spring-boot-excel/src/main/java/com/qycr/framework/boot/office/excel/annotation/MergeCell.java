package com.qycr.framework.boot.office.excel.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface MergeCell{

    int startRow() default -1;
    int endRow() default  -1;
    int startColumn() default -1;
    int endColumn() default  -1;
    Class<?>[] groups() default {};

}
