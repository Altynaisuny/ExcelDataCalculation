package com.example.excel.service;

import com.example.excel.facade.ExportExcelProduct;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IExcelOutputBiz {
    /**
     * 导出该销售商的数据
     * @param sheetName 销售商的名称
     * @param valuesList 该销售商的数据
     */
    void buildSheet(ExportExcelProduct exportExcelProduct, String sheetName, List<Map> valuesList);

    /**
     * 构建标题
     * @param exportExcelProduct
     * @param sheet
     */
    void buildTitle(ExportExcelProduct exportExcelProduct, Sheet sheet);
}
