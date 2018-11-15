package com.example.excel.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 导演类，代码侵入内部
 * controller调用此处
 */
@Service
public class Director {

    /**
     * 从excel中获取数据
     * @param inputStream
     * @param fileName
     * @return
     */
    public Map<String, List<Map>> getExcel(InputStream inputStream, String fileName){
        ExportAbstractExcelBuilder excelBuilder = new ExportAbstractExcelBuilder();
        ImportExcelProduct importExcelProduct = excelBuilder.setPart(inputStream, fileName);
        excelBuilder.buildExcel(importExcelProduct);
        return excelBuilder.resultMap;
    }

    /**
     * 把数据导出到excel
     */
    public void exportExcel(Map<String,List<Map>> data){

    }

}