package com.example.excel.service.Impl;

import com.example.excel.domain.enumeration.orderScreenEnum;
import com.example.excel.facade.CommonMethods;
import com.example.excel.service.IExcelInputBo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IExcelInputBoImpl implements IExcelInputBo {
    @Override
    public List<Sheet> getSheetFromStream(InputStream inputStream, String fileName) {
        //创建Excel工作薄
        Workbook work = null;
        try {
            work = CommonMethods.getWorkbook(inputStream, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //excel 中所有sheet
        List<Sheet> sheets = new ArrayList<>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            Sheet sheetEach = work.getSheetAt(i);
            if (sheetEach != null) {
                sheets.add(sheetEach);
            }
        }
        return sheets;
    }

    @Override
    public Integer getExcelSellerIndexColumn(Sheet sheetExample) {
        //只使用第一张sheet
        Row row = sheetExample.getRow(0);
        int sellerColumnIndex = -1;
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getPhysicalNumberOfCells();
        //获取买家账号这一列的索引
        for (int i = firstCellNum; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            String cellValue = CommonMethods.getCellValue(cell);
            if ("买家账号".equals(cellValue)) {
                sellerColumnIndex = cell.getColumnIndex();
                break;
            }
        }
        return sellerColumnIndex;
    }

    @Override
    public Map<String, Object> getExcelHeader(Sheet sheetExample) {
        Map<String, Object> headerIndexMap = new HashMap<>();
        Row row = sheetExample.getRow(0);
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getPhysicalNumberOfCells();
        //需要取到的列的索引
        for (orderScreenEnum orderEnum : orderScreenEnum.values()) {
            int columnIndex = -1;
            for (int i = firstCellNum; i < lastCellNum; i++) {
                Cell cell = row.getCell(i);
                String cellValue = CommonMethods.getCellValue(cell);
                if (cellValue.equals(orderEnum.getText())) {
                    columnIndex = cell.getColumnIndex();
                    headerIndexMap.put(cellValue, columnIndex);
                    break;
                }
            }
            //没有找到该列
            if (columnIndex == -1){
                headerIndexMap.put(orderEnum.getText(), -1);
            }
        }
        return headerIndexMap;
    }
}
