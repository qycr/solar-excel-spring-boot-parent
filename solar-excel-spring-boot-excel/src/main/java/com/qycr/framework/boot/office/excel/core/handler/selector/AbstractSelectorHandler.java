package com.qycr.framework.boot.office.excel.core.handler.selector;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;

public  abstract class AbstractSelectorHandler implements SelectorHandler {


    private    static  final char  CHARACTER = 'A';

    protected  static   final String HIDDEN = "hidden";

    private static final int CHAR_Y= 0x19;

    private static final int CHAR_Z= 0x1a;

    private static final int CHAR_YZ= 0x33;

    private static final int CHAR_A= 0x1;

    protected SelectorContext selectorContext;


   protected AbstractSelectorHandler(SelectorContext selectorContext){
        this.selectorContext = selectorContext;
    }

    @Override
    public String builderExcelLine(int number) {

        final StringBuilder builder = new StringBuilder();
        final int firstLine  = number / CHAR_Z;
        final int secondLine = number % CHAR_Z;

        if (firstLine > 0) {
            builder.append( (char) ( CHARACTER + firstLine - CHAR_A));
        }
        builder.append( (char) (CHARACTER + secondLine));

        return builder.toString();
    }


    @Override
    public String builderExcelLine(int offset, int rowId, int columnCount) {
        char start = (char) (CHARACTER + offset);
        if (columnCount <= CHAR_Y) {
            char end = (char) (start + columnCount - CHAR_A);
            return builder(start,rowId,end);
        } else {
            char endPrefix = CHARACTER;
            char endSuffix;
            if ((columnCount - CHAR_Y) / CHAR_Z == 0 || columnCount == CHAR_YZ) {
                if ((columnCount - CHAR_Y) % CHAR_Z == 0) {
                    endSuffix = (char) (CHARACTER + CHAR_Y);
                } else {
                    endSuffix = (char) (CHARACTER + (columnCount - CHAR_Y) % CHAR_Z - CHAR_A);
                }
            } else {
                if ((columnCount - CHAR_Y) % CHAR_Z == 0) {
                    endSuffix = (char) (CHARACTER + CHAR_Y);
                    endPrefix = (char) (endPrefix + (columnCount - CHAR_Y) / CHAR_Z - CHAR_A);
                } else {
                    endSuffix = (char) (CHARACTER + (columnCount - CHAR_Y) % CHAR_Z - CHAR_A);
                    endPrefix = (char) (endPrefix + (columnCount - CHAR_Y) / CHAR_Z);
                }
            }
            return builder(start,rowId,(char) (endPrefix + endSuffix));
        }
    }

    private String builder(char start , int rowId , char end){
       return  new StringBuilder("$").append(start).append("$").append(rowId).append(":$").append(end).append("$").append(rowId).toString();
    }
    @Override
    public DataValidationConstraint createExplicitListConstraint(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        final Sheet sheet = writeSheetHolder.getSheet();
        final DataValidationHelper helper = sheet.getDataValidationHelper();

        final String hiddenName = HIDDEN + selectorContext.getStartColumn();
        final Workbook workbook = writeWorkbookHolder.getWorkbook();
        final Sheet hidden = workbook.createSheet(hiddenName);
        final Name categoryName = workbook.createName();
        categoryName.setNameName(hiddenName);

        String excelLine = builderExcelLine(selectorContext.getStartColumn());
        String[] values = selectorContext.getSource();
        for (int i = 0, length = values.length; i < length; i++) {
            Row row = hidden.getRow(i);
            if (row == null) {
                row = hidden.createRow(i);
            }
            row.createCell(selectorContext.getStartColumn()).setCellValue(values[i]);
        }

        final StringBuilder builder = new StringBuilder("=");
        builder.append(hiddenName)
                .append("!$")
                .append(excelLine)
                .append("$1:$")
                .append(excelLine)
                .append("$")
                .append(values.length);
        return helper.createFormulaListConstraint(builder.toString());
    }




}
