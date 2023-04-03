package com.qycr.framework.boot.office.excel.core.listener;

import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.core.filter.ResultCommand;
import com.qycr.framework.boot.office.excel.core.support.FailureAnalyzer;
import com.qycr.framework.boot.office.excel.core.support.SimpleResultCommand;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class SimpleEasyExcelListener<R> implements EasyExcelListener<R> {

    private final ReadListener<R> readListener;

    private final  SimpleResultCommand<R> result = new SimpleResultCommand<>();

    private  List<FailureAnalyzer> failureAnalyzers;


    public SimpleEasyExcelListener(final Integer initializer , Integer startRowIndex){
        this.readListener = GenericListener.readListener(parameter -> result.setResult((R)parameter),initializer,this::failureAnalyzer, result::setThrowable,startRowIndex);
    }

    public SimpleEasyExcelListener(){
        this(10000,1);
    }

    public SimpleEasyExcelListener(ReadListener<R> readListener){
        this.readListener = readListener;
    }

    @Override
    public ReadListener<R> getListener() {
        return this.readListener;
    }

    @Override
    public ResultCommand getResult() {
        return this.result;
    }

    @Override
    public List<FailureAnalyzer> getFailureAnalyzer() {
        return this.failureAnalyzers;
    }

    private void failureAnalyzer(FailureAnalyzer failureAnalyzer) {
       if (CollectionUtils.isEmpty(failureAnalyzers)) {
           this.failureAnalyzers = new ArrayList<>();
       }
        failureAnalyzers.add(failureAnalyzer);
    }
}
