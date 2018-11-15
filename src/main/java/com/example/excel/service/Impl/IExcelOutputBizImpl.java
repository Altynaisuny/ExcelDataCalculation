package com.example.excel.service.Impl;

import com.example.excel.domain.enumeration.orderScreenEnum;
import com.example.excel.facade.CommonMethods;
import com.example.excel.facade.ExportExcelProduct;
import com.example.excel.service.IExcelInputBiz;
import com.example.excel.service.IExcelOutputBiz;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IExcelOutputBizImpl implements IExcelOutputBiz {
    @Override
    public void buildSheet(ExportExcelProduct exportExcelProduct, String name, List<Map> valuesList) {
        XSSFWorkbook wb = exportExcelProduct.getXssfWorkbook();
        String[] title = exportExcelProduct.getTitle();
        Map<String, Sheet> sheetMap = exportExcelProduct.getSheetsMap();
        XSSFSheet sheet = wb.createSheet(name);
        for (Map.Entry<String, Sheet> entry: sheetMap.entrySet()){
            if (entry.getKey().equals(name)){
                sheet = (XSSFSheet) entry.getValue();
            }
        }
        buildTitle(exportExcelProduct, sheet);
        //创建内容
        for (int i = 0; i < valuesList.size(); i++) {
            //创建行
            XSSFRow row = sheet.createRow(i + 1);
            //行数据
            Map<String, Object> rowMap = valuesList.get(i);
            //当前列索引
            int j = 0;
            for (orderScreenEnum screenEnum : orderScreenEnum.values()){
                Iterator iterator = rowMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    Object key = entry.getKey();
                    if (key.equals(screenEnum.getText())){
                        Object value = entry.getValue();
                        //将内容按顺序赋给对应的列对象
                        XSSFCell contentCell = row.createCell(j);
                        if (value == null){
                            contentCell.setCellValue("");
                        } else if (value.toString().matches("^(-?\\d+)(\\.\\d+)?$")){
                            //数值型
                            contentCell.setCellValue(Double.parseDouble(value.toString()));
                        } else {
                            //字符型
                            contentCell.setCellValue(value.toString());
                        }
                        XSSFCellStyle normalStyle = CommonMethods.sheetStyle(wb,"normal");
                        contentCell.setCellStyle(normalStyle);
                        break;
                    }
                }
                //列索引
                j ++;
            }
        }

    }
    @Override
    public void buildTitle(ExportExcelProduct exportExcelProduct, Sheet sheet) {
        String[] title = exportExcelProduct.getTitle();
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = (XSSFRow) sheet.createRow(0);
        XSSFWorkbook wb = exportExcelProduct.getXssfWorkbook();
        XSSFCellStyle headerStyle = CommonMethods.sheetStyle(wb, "header");
        //创建标题
        for (int i = 0; i < title.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 160*25);
        }
    }

}
