package com.qycr.framework.boot.office.excel.context;

import com.qycr.framework.boot.office.excel.core.filter.FilterType;
import com.qycr.framework.boot.office.excel.core.filter.ResolverFilter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;
import org.springframework.util.StringValueResolver;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import java.util.List;
import java.util.stream.Collectors;

public  abstract class AbstractHandlerMethodReturnValueHandler extends WebApplicationObjectSupport implements InitializingBean, EmbeddedValueResolverAware, DisposableBean {


    private  List<ResolverFilter> resolverFilters;

    protected List<ResolverFilter> getResolverFilters() {
        if (resolverFilters == null) {
            ApplicationContext applicationContext = getApplicationContext();
            Assert.notNull(applicationContext,"'applicationContext' must be not null");
            resolverFilters = applicationContext.getBeanProvider(ResolverFilter.class).stream().
                    filter(resolverFilter -> resolverFilter.filterType() == FilterType.RESPONSE ||
                            resolverFilter.filterType() == FilterType.GENERIC).collect(Collectors.toList());
            this.resolverFilters.sort(AnnotationAwareOrderComparator.INSTANCE);
        }
        return resolverFilters;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        getResolverFilters();
        resolverFilters.stream().forEach(ResolverFilter::init);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {

    }

    @Override
    public void destroy() throws Exception {
        for (int offset = resolverFilters.size() - 1; offset >= 0;  offset--) {
            this.resolverFilters.get(offset).destroy();
        }
    }
}