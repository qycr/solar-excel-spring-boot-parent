package com.qycr.framework.boot.office.excel.core.filter.request;

import com.qycr.framework.boot.office.excel.core.filter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class LoggerResolverFilter implements ResolverFilter {

    private boolean enabled = false;

    private static final Logger logger = LoggerFactory.getLogger("request-import-excel-resolver");

    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws Exception {
        StopWatch stopWatch = null;
        try {
            if (enabled) {
                stopWatch = new StopWatch(Thread.currentThread().getName());
                stopWatch.start();
            }
            return chain.doFilter(invocation);
        } finally {
            if (stopWatch != null) {
                stopWatch.stop();
                logger.info("Time spent parsing parameters :[{}]", stopWatch.getTotalTimeMillis());
            }
        }
    }

    @Override
    public FilterType filterType() {
        return FilterType.GENERIC;
    }

    @Override
    public int getOrder() {
        return -1001;
    }
}
