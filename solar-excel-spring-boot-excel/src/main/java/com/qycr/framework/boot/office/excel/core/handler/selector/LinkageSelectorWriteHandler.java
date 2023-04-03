package com.qycr.framework.boot.office.excel.core.handler.selector;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkageSelectorWriteHandler extends SimpleSelectorWriteHandler{


    public LinkageSelectorWriteHandler(SelectorContext selectorContext) {
        super(selectorContext);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (!selectorContext.isLinkage()){
            super.afterSheetCreate(writeWorkbookHolder, writeSheetHolder);
        }
        else {
            dropDownCheckBoxLing(writeWorkbookHolder.getWorkbook(), writeSheetHolder.getSheet());
        }
    }


    private  void createDataValidationListable(Workbook workbook, Sheet sheet, List<String> firstListable, CharacterProvider characterProvider, Integer startRow, Integer level) {

        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        String[] data = firstListable.toArray(new String[0]);
        Sheet hidden = workbook.createSheet(HIDDEN);
        Cell cell;
        for (int offset = 0; offset < data.length; offset++) {
            String name = data[offset];
            Row row = hidden.createRow(offset);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }

        Name name = workbook.createName();
        name.setNameName(HIDDEN);
        name.setRefersToFormula(HIDDEN +"!$A$1:$A$" + data.length);

        XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(DataValidationConstraint.ValidationType.LIST, HIDDEN);
        CellRangeAddressList firstRangeAddressList = new CellRangeAddressList(startRow - 1, 65535, characterProvider.getValue() - 1, characterProvider.getValue() - 1);
        DataValidation dataValidation = dataValidationHelper.createValidation(constraint, firstRangeAddressList);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.createErrorBox("提示", "与单元格的值不一致");
        dataValidation.createPromptBox(selectorContext.getPromptBox().getTitle(), selectorContext.getPromptBox().getText());
        dataValidation.setShowErrorBox(true);
        workbook.setSheetHidden(workbook.getSheetIndex(hidden), true);
        sheet.addValidationData(dataValidation);
        for (int offset = 0; offset <= CharacterProvider.values().length ; offset++) {
            createDataValidationConstraint(characterProvider.getCharacter(), sheet, offset + 1, characterProvider.getValue(), level);
        }

    }

    private  void createDataValidationConstraint(char offset, Sheet sheet, int rowNumber, int columnNumber, Integer level) {
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        for (int offset_ = 0; offset_ < level - 1; offset_++) {
            sheet.addValidationData(formulaDataValidationConstraint("INDIRECT($" + (char) (offset + offset_) + (rowNumber) + ")", rowNumber - 1, columnNumber + offset_, dvHelper));
        }
    }

    private  DataValidation formulaDataValidationConstraint(String formulaString, int rowIndex, int columnIndex, DataValidationHelper dataValidationHelper) {
        DataValidationConstraint dvConstraint = dataValidationHelper.createFormulaListConstraint(formulaString);
        CellRangeAddressList regions = new CellRangeAddressList(rowIndex, 65535, columnIndex, columnIndex);
        DataValidation dataValidation = dataValidationHelper.createValidation(dvConstraint, regions);
        dataValidation.setEmptyCellAllowed(false);
        dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);

        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.createErrorBox(selectorContext.getErrorBox().getTitle(), selectorContext.getErrorBox().getText());
        dataValidation.createPromptBox(selectorContext.getPromptBox().getTitle(), selectorContext.getPromptBox().getText());
        return dataValidation;
    }


    protected   void dropDownCheckBoxLing(Workbook workbook, Sheet sheet) {

        Sheet hiddenSheet = workbook.createSheet("SKY_SOLAR_SHEET");
        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);
        List<String> firstListable ;
        List<SelectorLinkageCorrelation> correlation = selectorContext.getSelector().correlation();
        if (!CollectionUtils.isEmpty(correlation)){
               LinkageCorrelationTree linkageCorrelationTree  = parserLinkageCorrelationTree(correlation);
               doLinkageCorrelation(builderFirstWriteData(hiddenSheet,firstListable = linkageCorrelationTree.firstListable),workbook,hiddenSheet,linkageCorrelationTree.parents,linkageCorrelationTree.mapping);
        }
        else {
              doSelectorJoin(builderFirstWriteData(hiddenSheet, firstListable = Arrays.asList(selectorContext.getSelector().selector())),workbook,hiddenSheet ,selectorContext.getSelector().join());
        }
        Stream.of(CharacterProvider.values()).filter(charStatusEnum -> charStatusEnum.getValue() == selectorContext.getStartColumn() + 1).forEach(
                charStatusEnum -> createDataValidationListable(workbook, sheet, firstListable, charStatusEnum, selectorContext.getStartRow(), selectorContext.getLevel()));

    }

    private LinkageCorrelationTree parserLinkageCorrelationTree(List<SelectorLinkageCorrelation> correlations) {
        List<String> firstListable = correlations.stream().filter(correlation -> correlation.getParentId() == 0).sorted((o1, o2) -> o2.getId() -o1.getId()).map(SelectorLinkageCorrelation::getName).collect(Collectors.toList());
        Map<Integer, List<SelectorLinkageCorrelation>> mapping = correlations.stream().collect(Collectors.groupingBy(SelectorLinkageCorrelation::getParentId));
        List<SelectorLinkageCorrelation> parents = correlations.stream().filter(correlation -> mapping.containsKey(correlation.getId())).sorted((o1, o2) -> o2.getId() -o1.getId()).collect(Collectors.toList());
        return new LinkageCorrelationTree(firstListable,parents,mapping);
    }

    private void doSelectorJoin(int rowId,Workbook workbook, Sheet hiddenSheet, Map<String, List<String>> mapping) {

        int newRowId = rowId;
        Iterator<String> iterator = mapping.keySet().iterator();
        while (iterator.hasNext()) {
            String mappingName = iterator.next();
            List<String> children = mapping.get(mappingName);
            Row siteRow = hiddenSheet.createRow(newRowId ++);

            siteRow.createCell(0).setCellValue(mappingName);
            for (int offset = 0; offset < children.size(); offset++) {
                siteRow.createCell(offset + 1).setCellValue(children.get(offset));
            }
            String range = builderExcelLine(1, newRowId, children.size());
            Name name = workbook.createName();
            name.setNameName(mappingName);
            String formula = hiddenSheet.getSheetName() + "!" + range;
            name.setRefersToFormula(formula);
        }
    }



    private  void doLinkageCorrelation(int rowId ,Workbook workbook, Sheet sheet, List<SelectorLinkageCorrelation> parents, Map<Integer,List<SelectorLinkageCorrelation>> mapping) {

        int newRowId = rowId;
        Iterator<SelectorLinkageCorrelation> iterator = parents.iterator();
        while (iterator.hasNext()) {
            SelectorLinkageCorrelation selectorLinkageCorrelation = iterator.next();
            List<SelectorLinkageCorrelation> children = mapping.get(selectorLinkageCorrelation.getId());
            Row siteRow = sheet.createRow(newRowId ++);

            siteRow.createCell(0).setCellValue(selectorLinkageCorrelation.getName());
            for (int offset = 0; offset < children.size(); offset++) {
                siteRow.createCell(offset + 1).setCellValue(children.get(offset).getName());
            }
            String  range = builderExcelLine(1, newRowId, children.size());
            Name name = workbook.createName();
            name.setNameName(selectorLinkageCorrelation.getName());
            String formula = sheet.getSheetName() + "!" + range;
            name.setRefersToFormula(formula);
        }
    }

    private class LinkageCorrelationTree {

        private List<String> firstListable;

        private List<SelectorLinkageCorrelation> parents;

        private Map<Integer,List<SelectorLinkageCorrelation>> mapping;

        public LinkageCorrelationTree(List<String> firstListable, List<SelectorLinkageCorrelation> parents, Map<Integer, List<SelectorLinkageCorrelation>> mapping) {
            this.firstListable = firstListable;
            this.parents = parents;
            this.mapping = mapping;
        }
    }

    private  int builderFirstWriteData(Sheet sheet, List<String> firstListable) {
         int  rowId  = -1 ;
         Row firstRow = sheet.createRow(  ++ rowId );
         firstRow.createCell(0).setCellValue("first");
         for (int offset = 0; offset < firstListable.size(); offset++) {
             firstRow.createCell(offset + 1).setCellValue(firstListable.get(offset));
         }
         return rowId;
     }

}
