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
    public void exportSheet(ExportExcelProduct exportExcelProduct, String sheetName, List<Map> valuesList) {
        XSSFWorkbook wb = exportExcelProduct.getXssfWorkbook();
        String[] title = exportExcelProduct.getTitle();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格样式
        XSSFCellStyle normalStyle = CommonMethods.sheetStyle(wb,"normal");
        XSSFCellStyle headerStyle = CommonMethods.sheetStyle(wb, "header");
        //声明列对象
        XSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 160*25);
        }
        //创建内容
        for (int i = 0; i < valuesList.size(); i++) {
            //创建行
            row = sheet.createRow(i + 1);
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
    public void writeSheet(ExportExcelProduct exportExcelProduct, List<Map> valuesList) {
    }

}
