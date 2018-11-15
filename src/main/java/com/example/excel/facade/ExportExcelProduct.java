package com.example.excel.facade;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public class ExportExcelProduct {
    private Map<String, Sheet> sheetsMap;
    private Map<String, List<Map>> resultMap;
    private XSSFWorkbook xssfWorkbook;
    private String[] title;

    public Map<String, Sheet> getSheetsMap() {
        return sheetsMap;
    }

    public void setSheetsMap(Map<String, Sheet> sheetsMap) {
        this.sheetsMap = sheetsMap;
    }

    public Map<String, List<Map>> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, List<Map>> resultMap) {
        this.resultMap = resultMap;
    }

    public XSSFWorkbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    public void setXssfWorkbook(XSSFWorkbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }
}
