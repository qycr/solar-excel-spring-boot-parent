package com.qycr.framework.boot.office.excel.core.parser;

import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.core.listener.GenericListener;
import com.qycr.framework.boot.office.excel.core.support.FailureAnalyzer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SimpleEasyExcelResolver<R> extends AbstractExcelResolver<R> {

    private final int  limiter;
    private final int startRowIndex;
    private  R result;
    private Throwable throwable;
    private  List<FailureAnalyzer> failureAnalyzers;


    public SimpleEasyExcelResolver(final Integer limiter , final Integer startRowIndex){
        this.limiter = limiter;
        this.startRowIndex = startRowIndex;
    }


    public SimpleEasyExcelResolver(){
        this(10000,1);
    }


    private void failureAnalyzer(FailureAnalyzer failureAnalyzer) {
        if (CollectionUtils.isEmpty(failureAnalyzers)) {
            this.failureAnalyzers = new ArrayList<>();
        }
        failureAnalyzers.add(failureAnalyzer);
    }


    @Override
    protected Throwable throwable() {
        return this.throwable;
    }

    @Override
    protected ReadListener<R> getReadListener() {
      return GenericListener.readListener(parameter -> result = (R)parameter,limiter,this::failureAnalyzer, t->throwable = t,startRowIndex);

    }

    @Override
    public R getResult() {
        return this.result;
    }

    @Override
    public List<FailureAnalyzer> getFailureAnalyzers() {
        return this.failureAnalyzers;
    }
}
