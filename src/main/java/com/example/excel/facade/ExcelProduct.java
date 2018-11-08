package com.example.excel.facade;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public class ExcelProduct {
    private Map<String, Object> excelHeaderMap;
    private Integer sellerIndexColumn;
    private Sheet sheetExample;

    public Map<String, Object> getExcelHeaderMap() {
        return excelHeaderMap;
    }

    public void setExcelHeaderMap(Map<String, Object> excelHeaderMap) {
        this.excelHeaderMap = excelHeaderMap;
    }

    public Integer getSellerIndexColumn() {
        return sellerIndexColumn;
    }

    public void setSellerIndexColumn(Integer sellerIndexColumn) {
        this.sellerIndexColumn = sellerIndexColumn;
    }

    public Sheet getSheetExample() {
        return sheetExample;
    }

    public void setSheetExample(Sheet sheetExample) {
        this.sheetExample = sheetExample;
    }
}
