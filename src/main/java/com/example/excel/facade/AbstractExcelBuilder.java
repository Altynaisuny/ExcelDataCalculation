package com.example.excel.facade;

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
     * 产品
     */
    protected ImportExcelProduct importExcelProduct;
    /**
     * 代理商在Excel中的索引
     */
    private static Integer SELLER_COLUMN_INDEX = -1;

    public AbstractExcelBuilder() {
        resultMap = new HashMap<>();
        importExcelProduct = new ImportExcelProduct();
    }
    /**
     * 设置不同部分
     */
    protected abstract void setPart(InputStream inputStream, String fileName);
    /**
     * 建造
     * @return
     */
    public abstract void buildExcel(ImportExcelProduct importExcelProduct);
}