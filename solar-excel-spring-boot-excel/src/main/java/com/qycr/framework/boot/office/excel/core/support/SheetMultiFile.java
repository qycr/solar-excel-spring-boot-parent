package com.qycr.framework.boot.office.excel.core.support;

public class SheetMultiFile implements MultiFile{

    private final String sheetName;

    private final Integer sheetNo;

    public SheetMultiFile(String sheetName,Integer sheetNo){
        this.sheetName = sheetName;
        this.sheetNo = sheetNo;
    }

    @Override
    public String multiNames() {
        return this.sheetName;
    }

    @Override
    public Integer indexed() {
        return this.sheetNo;
    }
}