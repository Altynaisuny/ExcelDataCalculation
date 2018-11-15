package com.example.excel.service;

import com.example.excel.facade.ExportExcelProduct;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IExcelOutputBiz {
    /**
     * 导出该销售商的数据
     * @param sheetName 销售商的名称
     * @param valuesList 该销售商的数据
     */
    void exportSheet(ExportExcelProduct exportExcelProduct, String sheetName, List<Map> valuesList);

    /**
     * 写入sheet
     * @param exportExcelProduct
     * @param valuesList
     */
    void writeSheet(ExportExcelProduct exportExcelProduct, List<Map> valuesList);
}
