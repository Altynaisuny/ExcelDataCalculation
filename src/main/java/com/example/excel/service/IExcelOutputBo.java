package com.example.excel.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IExcelOutputBo extends IExcelInfo {
    void exportXSSFWorkbook(String sheetName, String[] title, List<Map> valuesList, XSSFWorkbook wb);
}
