package com.qycr.framework.boot.office.excel.core.filter;

import org.springframework.core.Ordered;

public interface ResolverFilter extends Ordered {

    default void init() {}

    ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception;

    default void destroy(){}

    FilterType filterType();

}
