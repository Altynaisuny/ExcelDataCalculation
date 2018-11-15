package com.example.excel.service.Impl;

import com.example.excel.domain.enumeration.orderScreenEnum;
import com.example.excel.facade.CommonMethods;
import com.example.excel.service.IExcelInputBiz;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class IExcelInputBizImpl implements IExcelInputBiz {

    @Override
    public Map<String, Object> buildSellerMap(Sheet sheetExample, Integer sellerColumnIndex) {
        Map<String, Object> sellerMap = new HashMap<>();
        for (int j = sheetExample.getFirstRowNum() + 1; j <= sheetExample.getLastRowNum(); j++) {
            Row rowExample = sheetExample.getRow(j);
            String sellerName = rowExample.getCell(sellerColumnIndex).toString();
            sellerMap.forEach((key, value)->{
                if (!sellerMap.containsKey(sellerName)){
                    List<Map> list = new ArrayList<>();
                    sellerMap.put(sellerName, list);
                }
            });
        }
        return sellerMap;
    }

    @Override
    public Map<String, List<Map>> buildDataBySeller(Sheet sheetExample, Map<String, Object> headerIndexMap, Integer sellerIndexColumn) {
        Map<String, List<Map>> resultMap = new HashMap<>();
        for (int j = sheetExample.getFirstRowNum() + 1; j <= sheetExample.getLastRowNum(); j++) {
            Row row = sheetExample.getRow(j);
            //当前行归属哪一家代理商
            String sellerName = CommonMethods.getCellValue(row.getCell(sellerIndexColumn));
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
            if (resultMap.containsKey(sellerName)) {
                List<Map> sellerExampleList = resultMap.get(sellerName);
                if (!mergeSameData(dataMap, sellerExampleList)){
                    sellerExampleList.add(dataMap);
                }
            } else {
                //无代理商
                List<Map> sellerExampleList = new ArrayList<>();
                sellerExampleList.add(dataMap);
                resultMap.put(sellerName, sellerExampleList);
            }
        }
        return resultMap;
    }

    @Override
    public boolean mergeSameData(Map<String, Object> recentMap, List<Map> sellerList){
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
}
