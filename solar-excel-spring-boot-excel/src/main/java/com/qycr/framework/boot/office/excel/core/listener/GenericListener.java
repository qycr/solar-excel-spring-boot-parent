package com.qycr.framework.boot.office.excel.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.annotation.SolarLine;
import com.qycr.framework.boot.office.excel.core.support.FailureAnalyzer;
import com.qycr.framework.boot.office.excel.core.support.validation.SimpleValidatedProvider;
import com.qycr.framework.boot.office.excel.core.support.validation.ValidatedProvider;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ErrorHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class GenericListener {

    static final ValidatedProvider provider = SimpleValidatedProvider.getInstance();

    private GenericListener() {

    }

    public static  ReadListener readListener(Consumer<List<Object>> listener, Integer initializer, Consumer<FailureAnalyzer> consumer, ErrorHandler errorHandler, Integer startRowIndex) {

        return new AnalysisEventListener() {

            int lineNum = startRowIndex;
            final List<Object> cache = new ArrayList<>();
            final AtomicBoolean initStatus = new AtomicBoolean(false);
            final AtomicReference<Field> fd = new AtomicReference<>(null);

            @Override
            public void invoke(Object data, AnalysisContext context) {
                lineNum ++ ;
                Integer rowIndex = context.readRowHolder().getRowIndex() + startRowIndex;
                if (lineNum == initializer) {
                    errorHandler.handleError(new RuntimeException("Beyond the import limit range data"));
                    context.readRowHolder().setRowType(RowTypeEnum.EMPTY);
                }
                if (!initStatus.get()) {
                    ReflectionUtils.doWithFields(data.getClass(), field -> {
                        ReflectionUtils.makeAccessible(field);
                        fd.set(field);
                        initStatus.set(true);
                    }, field -> field.isAnnotationPresent(SolarLine.class));
                }
                final Set<ConstraintViolation<Object>> constraintViolationSet = provider.validate(data);
                if (CollectionUtils.isEmpty(constraintViolationSet)) {
                    if (!ObjectUtils.isEmpty(fd.get())) {
                        ReflectionUtils.setField(fd.get(), data, rowIndex);
                    }
                    cache.add(data);

                } else {
                    final String errorMessage = constraintViolationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));

                    consumer.accept(new FailureAnalyzer() {
                        @Override
                        public int line() {
                            return rowIndex;
                        }

                        @Override
                        public int column() {
                            return 0;
                        }

                        @Override
                        public String message() {
                            return errorMessage;
                        }

                        @Override
                        public Throwable throwable() {
                            return null;
                        }

                        @Override
                        public <T> T rawData() {
                            return (T)data;
                        }
                    });
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                listener.accept(cache);
            }

            @Override
            public boolean hasNext(AnalysisContext context) {
                if (context.readRowHolder().getRowType().equals(RowTypeEnum.EMPTY)) {
                    doAfterAllAnalysed(context);
                    return false;
                }
                return super.hasNext(context);
            }

        };
    }

}
