package com.example.excel.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;

public interface IExcelInputBiz extends IExcelInfo{
    /**
     * 构建销售商
     * @param sheetExample sheet
     * @param sellerColumnIndex 销售商所在字段
     * @return
     */
    Map<String, Object> buildSellerMap(Sheet sheetExample, Integer sellerColumnIndex);
    /**
     * @param sheetExample 当前sheet
     * @param headerIndexMap 需要导出的excel 列
     * @param sellerIndexColumn 销售商这一列
     */
    Map<String, List<Map>> buildDataBySeller(Sheet sheetExample, Map<String, Object> headerIndexMap, Integer sellerIndexColumn);
    /**
     * 合并数据
     * @param recentMap
     * @param sellerList
     * @return
     */
    boolean mergeSameData(Map<String, Object> recentMap, List<Map> sellerList);
}
