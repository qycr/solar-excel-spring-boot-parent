package com.qycr.framework.boot.office.excel.annotation;

import com.alibaba.excel.write.handler.WriteHandler;
import com.qycr.framework.boot.office.excel.core.handler.selector.LinkageSelectorWriteHandler;
import com.qycr.framework.boot.office.excel.core.handler.selector.ParserStrategy;
import com.qycr.framework.boot.office.excel.core.handler.selector.SelectorParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Selector {

    String[] source() default {};

    Class<? extends SelectorParser> selector() default SelectorParser.class;

    String selectorExpression() default "";

    Class<? extends WriteHandler> writeHandler() default LinkageSelectorWriteHandler.class;

    boolean multi() default false;

    boolean primary() default true;

    int fistRow() default 0;

    int lastRow() default 0;

    int fistColumn() default 0;

    int lastColumn() default 0;

    boolean  linkage() default false;

    int columnLevel() default 1;

    ParserStrategy strategy() default ParserStrategy.SPRING;



}
