package com.qycr.framework.boot.office.excel.core.handler.selector;

@FunctionalInterface
public interface ValueProvider<T> {

    T  getValue();
}
