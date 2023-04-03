package com.qycr.framework.boot.office.excel.core.filter;


import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@Deprecated
public class SimpleResolverFilterChain  implements ResolverFilterChain {

    private final List<ResolverFilter> allFilters;

    @Nullable
    private final ResolverFilter currentFilter;

    private final ExcelHandler excelHandler;

    @Nullable
    private final SimpleResolverFilterChain chain;


    public SimpleResolverFilterChain(List<ResolverFilter> filters, ExcelHandler excelHandler) {
        this.allFilters = Collections.unmodifiableList(filters);
        this.excelHandler = excelHandler;
        SimpleResolverFilterChain resolverFilterChain = initChain(filters, excelHandler);
        this.currentFilter = resolverFilterChain.currentFilter;
        this.chain = resolverFilterChain.chain;
    }

    private static SimpleResolverFilterChain initChain(List<ResolverFilter> filters, ExcelHandler excelHandler) {
        SimpleResolverFilterChain resolverFilterChain = new SimpleResolverFilterChain(excelHandler, filters, null, null);
        ListIterator<? extends ResolverFilter> iterator = filters.listIterator(filters.size());
        while (iterator.hasPrevious()) {
            resolverFilterChain = new SimpleResolverFilterChain(excelHandler, filters, iterator.previous(), resolverFilterChain);
        }
        return resolverFilterChain;
    }


    private SimpleResolverFilterChain(ExcelHandler excelHandler, List<ResolverFilter> allFilters, @Nullable ResolverFilter currentFilter, @Nullable SimpleResolverFilterChain resolverFilterChain) {
        this.excelHandler = excelHandler;
        this.allFilters = allFilters;
        this.currentFilter = currentFilter;
        this.chain = resolverFilterChain;
    }

    public List<ResolverFilter> getFilters() {
        return this.allFilters;
    }

    @Override
    public ResultCommand doFilter(Invocation invocation) throws Exception {
        return this.currentFilter != null && this.chain != null ? currentFilter.filter(invocation, chain) : this.excelHandler.handler(invocation);
    }

}