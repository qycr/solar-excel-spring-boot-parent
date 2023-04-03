package com.qycr.framework.boot.office.excel.core.filter.response;

import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.qycr.framework.boot.office.excel.annotation.Selector;
import com.qycr.framework.boot.office.excel.core.exception.SolarExcelException;
import com.qycr.framework.boot.office.excel.core.filter.*;
import com.qycr.framework.boot.office.excel.core.handler.selector.ParserStrategy;
import com.qycr.framework.boot.office.excel.core.handler.selector.SelectorContext;
import com.qycr.framework.boot.office.excel.core.handler.selector.SelectorParser;
import com.qycr.framework.boot.office.excel.core.handler.selector.SolarExcelBox;
import com.qycr.framework.boot.office.excel.core.support.SolarExcelAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.ResolvableType;
import org.springframework.expression.BeanResolver;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;

public class SelectorResolverFilter implements ResolverFilter , BeanFactoryAware {

    private BeanResolver beanResolver;

    private BeanFactory beanFactory;

    private final SolarExcelBox  errorBox;

    private final SolarExcelBox  promptBox;


    public SelectorResolverFilter(SolarExcelBox  errorBox , SolarExcelBox  promptBox){
            this.errorBox = errorBox;
            this.promptBox = promptBox;
    }


    @Override
    public ResultCommand filter(Invocation invocation, ResolverFilterChain chain) throws  Exception {

        ExcelWriterBuilder excelWriterBuilder = invocation.getProperty(SolarExcelAttribute.EXCEL_WRITE_BUILDER, ExcelWriterBuilder.class);

        Class<?> target = ResolvableType.forMethodParameter(invocation.parameter()).resolveGeneric(0);

        ReflectionUtils.doWithFields(target,field -> {

            Selector selector = field.getAnnotation(Selector.class);
            Class<? extends WriteHandler> writeHandler = selector.writeHandler();
            SelectorContext.Builder builder = SelectorContext.builder();
            builder.selectorExpression(selector.selectorExpression())
                    .resolver(this.beanResolver)
                    .level(selector.columnLevel())

                    .lastRow(selector.lastRow())
                    .linkage(selector.linkage())
                    .multi(selector.multi())

                    .primary(selector.primary())
                    .lastColumn(selector.lastColumn())
                    .source(selector.source())
                    .promptBox(this.promptBox)
                    .errorBox(this.errorBox)

                    .startColumn(selector.fistColumn())
                    .startRow(selector.fistRow())

                    .selector(selector.selector() == SelectorParser.class ? null : builderSelector(selector.selector(),selector.strategy()));

            Constructor<? extends WriteHandler> constructor ;
            try {
                constructor = ReflectionUtils.accessibleConstructor(writeHandler, SelectorContext.class);
            } catch (NoSuchMethodException e) {
                throw new SolarExcelException(e);
            }
            final WriteHandler handler = BeanUtils.instantiateClass(constructor,builder.build());
            excelWriterBuilder.registerWriteHandler(handler);

        },field -> field.isAnnotationPresent(Selector.class));


        return chain.doFilter(invocation);
    }

    private SelectorParser builderSelector(Class<? extends SelectorParser> selector , ParserStrategy parserStrategy) {

        if (parserStrategy == ParserStrategy.SPRING) {
            return this.beanFactory.getBean(selector);
        }
        return BeanUtils.instantiateClass(selector);
    }

    @Override
    public FilterType filterType() {
        return FilterType.RESPONSE;
    }

    @Override
    public int getOrder() {
        return -700;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanResolver = new BeanFactoryResolver(beanFactory);
        this.beanFactory = beanFactory;
    }
}
