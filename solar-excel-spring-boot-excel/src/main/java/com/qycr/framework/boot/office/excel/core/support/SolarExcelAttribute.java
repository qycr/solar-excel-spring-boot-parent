package com.qycr.framework.boot.office.excel.core.support;

import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import org.springframework.web.bind.WebDataBinder;

public final class SolarExcelAttribute {

    private SolarExcelAttribute(){

    }

    public  static final String  REQUEST_IMPORT_DEFINITION = RequestImportDefinition.class.getCanonicalName();

    public  static final String  RESPONSE_EXPORT_DEFINITION = ResponseExportDefinition.class.getCanonicalName();

    public static final String  WEB_DATA_BINDER = WebDataBinder.class.getCanonicalName();

    public static final String  EXCEL_READER_BUILDER = ExcelReaderBuilder.class.getCanonicalName();

    public static final String  EXCEL_WRITE_BUILDER = ExcelWriterBuilder.class.getCanonicalName();

}
