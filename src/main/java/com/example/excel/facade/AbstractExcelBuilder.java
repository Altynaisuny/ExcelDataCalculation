package com.example.excel.facade;

import com.example.excel.domain.enumeration.orderScreenEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractExcelBuilder {
    /**
     * 最终建造对象
     */
    protected Map<String, List<Map>> resultMap;
    /**
     * 代理商在Excel中的索引
     */
    private static Integer SELLER_COLUMN_INDEX = -1;

    public AbstractExcelBuilder() {
        resultMap = new HashMap<>();
    }
    /**
     * 设置不同部分
     */
    protected abstract ExcelProduct setPart(InputStream inputStream, String fileName);
    /**
     * 建造
     * @return
     */
    public abstract Map<String, List<Map>> buildExcel(ExcelProduct excelProduct);
}
