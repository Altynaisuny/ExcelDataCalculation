package com.example.excel.facade;

import com.example.excel.service.IExcelInputBiz;
import com.example.excel.service.IExcelInputBo;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportAbstractExcelBuilder extends AbstractExcelBuilder {
    @Autowired
    IExcelInputBiz iExcelInputBiz;
    @Autowired
    IExcelInputBo iExcelInputBo;
    @Override
    protected ExcelProduct setPart(InputStream inputStream, String fileName) {
        List<Sheet> sheets = iExcelInputBo.getSheetFromStream(inputStream, fileName);
        if (CollectionUtils.isEmpty(sheets)){
            return null;
        }
        ExcelProduct excelProduct = new ExcelProduct();
        Sheet sheetExample = sheets.get(0);
        excelProduct.setSheetExample(sheetExample);
        Map<String, Object> excelHeaderMap = iExcelInputBo.getExcelHeader(sheetExample);
        excelProduct.setExcelHeaderMap(excelHeaderMap);
        Integer sellerIndexColumn = iExcelInputBo.getExcelSellerIndexColumn(sheetExample);
        excelProduct.setSellerIndexColumn(sellerIndexColumn);
        return excelProduct;
    }

    @Override
    public Map<String, List<Map>> buildExcel(ExcelProduct excelProduct) {
        //构建代理商
        Integer sellerIndexColumn = excelProduct.getSellerIndexColumn();
        Sheet sheet = excelProduct.getSheetExample();
        Map<String, Object> sellerMap = iExcelInputBiz.buildSellerMap(sheet, sellerIndexColumn);
        //构建header
        Map<String, Object> headerMap = excelProduct.getExcelHeaderMap();
        Map<String, List<Map>> result = iExcelInputBiz.buildDataBySeller(sheet, headerMap, sellerIndexColumn);
        return result;
    }
}
