package com.qycr.framework.boot.office.excel.core.support;

import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;

public class SimpleResultCommand<T> implements ResultCommand {

    private T  result;

    private Throwable throwable;


    public SimpleResultCommand(T result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    public SimpleResultCommand() {

    }

    public void setResult(T result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }



    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public  T getResult() {
        return this.result;
    }

    @Override
    public Throwable throwable() {
        return this.throwable;
    }
}
