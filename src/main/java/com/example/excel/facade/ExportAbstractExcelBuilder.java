package com.example.excel.facade;

import com.example.excel.service.IExcelInputBiz;
import com.example.excel.service.IExcelInputBo;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ExportAbstractExcelBuilder extends AbstractExcelBuilder {
    @Autowired
    IExcelInputBiz iExcelInputBiz;
    @Autowired
    IExcelInputBo iExcelInputBo;
    @Override
    protected ImportExcelProduct setPart(InputStream inputStream, String fileName) {
        List<Sheet> sheets = iExcelInputBo.getSheetFromStream(inputStream, fileName);
        if (CollectionUtils.isEmpty(sheets)){
            return null;
        }
        ImportExcelProduct importExcelProduct = super.importExcelProduct;
        Sheet sheetExample = sheets.get(0);
        importExcelProduct.setSheetExample(sheetExample);
        Map<String, Object> excelHeaderMap = iExcelInputBo.getExcelHeader(sheetExample);
        importExcelProduct.setExcelHeaderMap(excelHeaderMap);
        Integer sellerIndexColumn = iExcelInputBo.getExcelSellerIndexColumn(sheetExample);
        importExcelProduct.setSellerIndexColumn(sellerIndexColumn);
        return importExcelProduct;
    }

    @Override
    public void buildExcel(ImportExcelProduct importExcelProduct) {
        //构建代理商
        Integer sellerIndexColumn = importExcelProduct.getSellerIndexColumn();
        Sheet sheet = importExcelProduct.getSheetExample();
        Map<String, Object> sellerMap = iExcelInputBiz.buildSellerMap(sheet, sellerIndexColumn);
        //构建header
        Map<String, Object> headerMap = importExcelProduct.getExcelHeaderMap();
        super.resultMap = iExcelInputBiz.buildDataBySeller(sheet, headerMap, sellerIndexColumn);
    }
}
