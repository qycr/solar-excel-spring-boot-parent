package com.qycr.framework.boot.office.excel.core.handler.selector;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.DataValidationConstraint;

public interface SelectorHandler {


    String builderExcelLine(int number);

    String builderExcelLine(int offset, int rowId, int columnCount);

    DataValidationConstraint createExplicitListConstraint(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder);

}
