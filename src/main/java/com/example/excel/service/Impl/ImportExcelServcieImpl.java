package com.example.excel.service.Impl;

import com.example.excel.domain.enumeration.orderScreenEnum;
import com.example.excel.facade.CommonMethods;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import com.example.excel.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportExcelServcieImpl implements ImportExcel {
    /**
     * 代理商在Excel中的索引
     */
    private static Integer SELLER_COLUMN_INDEX = -1;

    private static final Log LOGGER = LogFactory.getLog(ImportExcelServcieImpl.class);

    /**
     * 处理上传的文件
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, List<Map>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        //创建Excel工作薄
        Workbook work = CommonMethods.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("Excel工作薄为空！");
        }
        //excel 中所有sheet
        List<Sheet> sheets = new ArrayList<>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            Sheet sheetEach = work.getSheetAt(i);
            if (sheetEach != null) {
                sheets.add(sheetEach);
            } else {
                work.close();
            }
        }
        LOGGER.info("sheet总数" + sheets.size());
        //构建数据
        return buildData(sheets);
    }

    /**
     * 构建所有代理商数据
     * @param sheets
     */
    private Map<String, List<Map>> buildData(List<Sheet> sheets) {
        if (sheets == null || sheets.isEmpty()) {
            throw new NullPointerException("sheets is null !");
        }
        //获取表头
        Map<String, Object> headerIndexMap = getColumnHeader(sheets);
        Map<String, List<Map>> allSellerMap = new HashMap<>();
        //获取指定sheet
        Sheet sheetExample = sheets.get(0);
        //添加数据
        for (int j = sheetExample.getFirstRowNum() + 1; j <= sheetExample.getLastRowNum(); j++) {
            Row row = sheetExample.getRow(j);
            //按照代理商添加
            Map<String, Object> recentMap = buildDataBySeller(row, headerIndexMap, allSellerMap);
        }
        //求和
        getSumData(allSellerMap);
        return allSellerMap;
    }

    /**
     * 每个代理商
     *
     * @param row             当前行
     * @param headerIndexMap 需要提取数据的索引 key 列名 value 索引
     * @param allSellerMap    所有零售商的数据集合
     */
    private Map<String, Object> buildDataBySeller(Row row, Map<String, Object> headerIndexMap, Map<String, List<Map>> allSellerMap) {
        //当前行归属哪一家代理商
        String sellerName = CommonMethods.getCellValue(row.getCell(SELLER_COLUMN_INDEX));
        //行数据构建
        Map<String, Object> dataMap = new HashMap<>();
        for (orderScreenEnum screenEnum : orderScreenEnum.values()){
            headerIndexMap.forEach((key, value)->{
                if (key.equals(screenEnum.getText()) && (int)value != -1){
                    String cellValue = CommonMethods.getCellValue(row.getCell(Integer.parseInt(value.toString())));
                    dataMap.put(key, cellValue);
                }
            });
            if (!dataMap.containsKey(screenEnum.getText())){
                dataMap.put(screenEnum.getText(), null);
            }
        }

        //已有代理商
        if (allSellerMap.containsKey(sellerName)) {
            List<Map> sellerExampleList = allSellerMap.get(sellerName);
            if (!mergeData(dataMap, sellerExampleList)){
                sellerExampleList.add(dataMap);
            }
        } else {
            //无代理商
            List<Map> sellerExampleList = new ArrayList<>();
            sellerExampleList.add(dataMap);
            allSellerMap.put(sellerName, sellerExampleList);
        }
        return dataMap;
    }

    /**
     * 添加sheet表头索引
     * @param sheets
     * @return key 列名称 value 列索引
     */
    private Map<String, Object> getColumnHeader(List<Sheet> sheets) {
        Sheet sheetExample = sheets.get(0);
        //第一行
        Row row = sheetExample.getRow(0);
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getPhysicalNumberOfCells();
        //买家账号 列索引
        for (int i = firstCellNum; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            String cellValue = CommonMethods.getCellValue(cell);
            if ("买家账号".equals(cellValue)) {
                SELLER_COLUMN_INDEX = cell.getColumnIndex();
                break;
            }
        }
        Map<String, Object> headerIndexMap = new HashMap<>();
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

    /**
     * 检查是否 有相同数据
     * @param recentMap 本次要添加的数据
     * @param sellerList 该销售商数据集合
     * @return true 有相同 false 不同
     */
    private boolean mergeData(Map<String, Object> recentMap, List<Map> sellerList){
        String readyInsertId = recentMap.get("供应商款号").toString();
        int temp = -1;
        for (int i = 0; i < sellerList.size(); i++){
            //每条数据对应的销售商
            String id = sellerList.get(i).get(orderScreenEnum.id.getText()).toString();
            if (readyInsertId.equals(id)){
                temp = i;
                break;
            }
        }
        //
        if (temp != -1){
            Map<String, Object> lastSameDataMap = sellerList.get(temp);
            //合并
            for (Map.Entry<String, Object> entry : lastSameDataMap.entrySet()){
                Object value = entry.getValue();
                String key = entry.getKey();
                if (entry.getValue() == null){
                    continue;
                }
                //供应商字段不做处理
                if ("供应商款号".equals(key)){
                    continue;
                }
                //字段如果是空不做处理
                if (recentMap.get(key) == null || "".equals(recentMap.get(key))){
                    continue;
                }
                //如果是数字
                if (value.toString().matches("^(-?\\d+)(\\.\\d+)?$")){
                    double beforeValue = Double.parseDouble(value.toString());
                    double insertValue = Double.parseDouble(recentMap.get(key).toString());
                    entry.setValue(beforeValue + insertValue);
                }
            }
        }
        return temp != -1;
    }
    //求和
    private void getSumData(Map<String, List<Map>> allSellerMap){

    }
}
