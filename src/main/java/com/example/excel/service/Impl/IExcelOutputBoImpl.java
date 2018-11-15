package com.example.excel.service.Impl;

import com.example.excel.domain.enumeration.orderScreenEnum;
import com.example.excel.facade.CommonMethods;
import com.example.excel.facade.ExportExcelProduct;
import com.example.excel.service.IExcelOutputBo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class IExcelOutputBoImpl implements IExcelOutputBo {

    @Override
    public void getWorkBook(ExportExcelProduct exportExcelProduct) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        exportExcelProduct.setXssfWorkbook(xssfWorkbook);
    }

    @Override
    public void getSheetTitle(ExportExcelProduct exportExcelProduct) {
        String[] title = new String[10];
        for (int i = 0 ;i< orderScreenEnum.values().length; i ++){
            title[i] = orderScreenEnum.values()[i].getText();
        }
        exportExcelProduct.setTitle(title);
    }

    @Override
    public void getSheetBySeller(ExportExcelProduct exportExcelProduct, Map<String, List<Map>> resultMap) {
        XSSFWorkbook workbook = exportExcelProduct.getXssfWorkbook();
        if (CollectionUtils.isEmpty(resultMap)){
            return;
        }
        Map<String, Sheet> sheetsMap = new HashMap<>();
        resultMap.forEach((key, value)->{
            XSSFSheet sheet = workbook.createSheet(key);
            sheetsMap.put(key, sheet);
        });
        exportExcelProduct.setSheetsMap(sheetsMap);
    }
}
