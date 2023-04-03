package com.qycr.framework.boot.office.excel.core.support;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.*;

public final class PredicateConsumer {

    private static  PredicateConsumer predicateConsumer = new PredicateConsumer();

    private static final String SUPPLIER = "supplier must be not null";

    private PredicateConsumer() {

    }

    public static PredicateConsumer getInstance() {
        return predicateConsumer;
    }


    public <T> PredicateConsumer consumer(Consumer<T> consumer, T value) {
        if (value != null) {
            consumer.accept(value);
        }
        return this;
    }

    public <T1, T2> PredicateConsumer biConsumer(BiConsumer<T1, T2> consumer, T1 value1, T2 value2) {
        consumer.accept(value1, value2);
        return this;
    }

    public <T1, T2, T3> PredicateConsumer teConsumer(TeConsumer<T1, T2, T3> consumer, T1 value1, T2 value2, T3 value3) {
        consumer.accept(value1, value2, value3);
        return this;
    }

    public <T> PredicateConsumer consumerSupplier(Consumer<T> consumer, Supplier<T> supplier) {
        Assert.notNull(supplier, SUPPLIER);
        return consumer(consumer, supplier.get());
    }

    public <T, R> PredicateConsumer consumerSupplier(Consumer<T> consumer, Supplier<R> supplier, Function<R, T> function) {
        Assert.notNull(supplier, SUPPLIER);
        return consumerSupplier(consumer, supplier.get(), function);
    }

    public <T, R> PredicateConsumer consumerSupplier(Consumer<T> consumer, R value, Function<R, T> function) {
        Assert.notNull(value, "value must be not null");
        Assert.notNull(function, "function must be not null");
        return consumer(consumer, function.apply(value));
    }

    public PredicateConsumer longConsumer(LongConsumer consumer, Long value) {
        if (value != null && value > 0) {
            consumer.accept(value);
        }
        return this;
    }

    public PredicateConsumer longConsumerSupplier(LongConsumer consumer, Supplier<Long> longSupplier) {
        Assert.notNull(longSupplier, "longSupplier must be not null");
        return longConsumer(consumer, longSupplier.get());
    }


    public PredicateConsumer longConsumerSupplier(LongConsumer consumer, LongSupplier supplier, LongFunction<Long> longFunction) {
        Assert.notNull(longFunction, "longFunction must be not null");
        Assert.notNull(supplier, SUPPLIER);
        return longConsumer(consumer, longFunction.apply(supplier.getAsLong()));
    }


    public interface TeConsumer<T, U, R> {

        void accept(T t, U u, R r);

        default TeConsumer<T, U, R> andThen(TeConsumer<? super T, ? super U, ? super R> after) {
            Objects.requireNonNull(after);

            return (l, r, t) -> {
                accept(l, r, t);
                after.accept(l, r, t);
            };
        }
    }


}
