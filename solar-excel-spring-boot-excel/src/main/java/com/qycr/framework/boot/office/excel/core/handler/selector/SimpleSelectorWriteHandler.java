package com.qycr.framework.boot.office.excel.core.handler.selector;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleSelectorWriteHandler extends AbstractSelectorHandler implements SheetWriteHandler {


    public SimpleSelectorWriteHandler(SelectorContext selectorContext) {
        super(selectorContext);
    }


    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        final  String[] dropDownBox = ObjectUtils.isEmpty(selectorContext.getValue()) ? new String[]{""} : selectorContext.getValue();

        final String character = Stream.of(dropDownBox).collect(Collectors.joining());
        final Sheet sheet = writeSheetHolder.getSheet();
        final DataValidationHelper helper = sheet.getDataValidationHelper();

        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(selectorContext.getStartRow(), selectorContext.getLastRow(), selectorContext.getStartColumn(), selectorContext.getLastColumn());
        DataValidationConstraint constraint;
        if ( character.getBytes(Charset.defaultCharset()).length > 255 ) {
            constraint = createExplicitListConstraint(writeWorkbookHolder, writeSheetHolder);
        } else {
            constraint = helper.createExplicitListConstraint(dropDownBox);
        }
        DataValidation validation = helper.createValidation(constraint, cellRangeAddressList);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.setShowErrorBox(true);
        validation.setSuppressDropDownArrow(true);
        validation.createErrorBox(selectorContext.getErrorBox().getTitle(), selectorContext.getErrorBox().getText());
        validation.createPromptBox(selectorContext.getPromptBox().getTitle(), selectorContext.getPromptBox().getText());
        sheet.addValidationData(validation);

        int hiddenIndex = writeWorkbookHolder.getWorkbook().getSheetIndex( HIDDEN + selectorContext.getStartColumn());

        if (hiddenIndex != - 1 && !writeWorkbookHolder.getWorkbook().isSheetHidden(hiddenIndex)) {
            writeWorkbookHolder.getWorkbook().setSheetHidden(hiddenIndex, true);
        }
    }



}
