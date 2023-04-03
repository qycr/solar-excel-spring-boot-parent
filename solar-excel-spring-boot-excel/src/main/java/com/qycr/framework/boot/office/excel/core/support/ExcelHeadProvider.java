package com.qycr.framework.boot.office.excel.core.support;

import java.util.List;

@FunctionalInterface
public interface ExcelHeadProvider {

    List<List<String>> heads();

}
