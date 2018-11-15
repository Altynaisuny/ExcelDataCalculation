package com.example.excel.service;

import com.example.excel.facade.ExportExcelProduct;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IExcelOutputBo extends IExcelInfo {
    /**
     * 获取work工作薄
     * @return
     */
    void getWorkBook(ExportExcelProduct exportExcelProduct);

    /**
     * 获取sheet标题，具体要取那些项
     * @return
     */
    void getSheetTitle(ExportExcelProduct exportExcelProduct);

    /**
     * 获取sheet列表
     * @return
     */
    void getSheetBySeller(ExportExcelProduct exportExcelProduct, Map<String, List<Map>> resultMap);
}
