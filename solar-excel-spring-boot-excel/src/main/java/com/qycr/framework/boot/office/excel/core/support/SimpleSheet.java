package com.qycr.framework.boot.office.excel.core.support;

public class SimpleSheet {
    private int  index;

    private String sheetName;

    public SimpleSheet(int index, String sheetName) {
        this.index = index;
        this.sheetName = sheetName;
    }

    public int getIndex() {
        return index;
    }

    public String getSheetName() {
        return sheetName;
    }
}
