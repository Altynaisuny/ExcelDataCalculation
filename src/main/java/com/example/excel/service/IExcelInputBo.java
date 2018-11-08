package com.example.excel.service;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IExcelInputBo extends IExcelInfo {
    List<Sheet> getSheetFromStream(InputStream inputStream, String fileName);
    Map<String, Object> getExcelHeader(Sheet sheetExample);
    Integer getExcelSellerIndexColumn(Sheet sheetExample);
}