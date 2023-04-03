package com.qycr.framework.boot.office.excel.core.filter;

import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class DefaultResolverFilterChain implements ResolverFilterChain {

    private final List<ResolverFilter> allFilters;

    @Nullable
    private final ResolverFilter currentFilter;

    @Nullable
    private final DefaultResolverFilterChain chain;


    public DefaultResolverFilterChain(List<ResolverFilter> filters) {
        this.allFilters = Collections.unmodifiableList(filters);
        DefaultResolverFilterChain resolverFilterChain = initChain(filters);
        this.currentFilter = resolverFilterChain.currentFilter;
        this.chain = resolverFilterChain.chain;
    }

    private static DefaultResolverFilterChain initChain(List<ResolverFilter> filters) {
        DefaultResolverFilterChain resolverFilterChain = new DefaultResolverFilterChain(filters, null, null);
        ListIterator<? extends ResolverFilter> iterator = filters.listIterator(filters.size());
        while (iterator.hasPrevious()) {
            resolverFilterChain = new DefaultResolverFilterChain(filters, iterator.previous(), resolverFilterChain);
        }
        return resolverFilterChain;
    }


    private DefaultResolverFilterChain(List<ResolverFilter> allFilters, @Nullable ResolverFilter currentFilter, @Nullable DefaultResolverFilterChain resolverFilterChain) {
        this.allFilters = allFilters;
        this.currentFilter = currentFilter;
        this.chain = resolverFilterChain;
    }

    public List<ResolverFilter> getFilters() {
        return this.allFilters;
    }

    @Override
    public ResultCommand doFilter(Invocation invocation) throws Exception{

        if (this.currentFilter != null && this.chain != null) {
           return currentFilter.filter(invocation, chain);
        }
         throw new UnsupportedOperationException();
    }
}
