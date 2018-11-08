package com.example.excel.facade;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 导演类，代码侵入内部
 */
public class Director {

    @Autowired
    private ExportAbstractExcelBuilder excelBuilder;

    public Map<String, List<Map>> getExcel(InputStream inputStream, String fileName){
        ExcelProduct excelProduct = excelBuilder.setPart(inputStream, fileName);
        Map<String, List<Map>> resultMap = excelBuilder.buildExcel(excelProduct);
        return resultMap;
    }
}
