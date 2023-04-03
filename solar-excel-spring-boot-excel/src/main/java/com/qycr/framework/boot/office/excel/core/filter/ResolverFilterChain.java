package com.qycr.framework.boot.office.excel.core.filter;



@FunctionalInterface
public interface ResolverFilterChain {
    ResultCommand  doFilter(Invocation invocation) throws Exception;

}
