package com.example.excel.controller;

import com.example.excel.domain.enumeration.orderScreenEnum;
import com.example.excel.facade.CommonMethods;
import com.example.excel.facade.Director;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.excel.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    Director directorService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public void uploadExcel(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String fileName = file.getOriginalFilename();
        InputStream inputStream;
        Map<String,List<Map>> result = new HashMap<>();
        //导入
        try {
            inputStream = file.getInputStream();
            // TODO: 2018/11/8 调用
            result = directorService.getExcel(inputStream, fileName);
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        //导出
        try {
            this.export(response, result);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 导出报表
     * @return
     */
    public void export(HttpServletResponse response, Map<String,List<Map>> data) throws Exception {
        //获取数据
        //excel标题
        String[] title = {orderScreenEnum.id.getText(),orderScreenEnum.realSendNums.getText(),orderScreenEnum.salesMoney.getText(),
                orderScreenEnum.rejectGoodsNums.getText(),orderScreenEnum.rejectGoodsMoney.getText(),orderScreenEnum.costUnivalent.getText(),
                orderScreenEnum.salesCost.getText(), orderScreenEnum.freightIncomeAverage.getText(),orderScreenEnum.profit.getText(),
                orderScreenEnum.ProfitMargin.getText()
        };
        //excel文件名
        String fileName = LocalTime.now() + "月销售汇总" + ".xlsx";
        XSSFWorkbook wb = new XSSFWorkbook();
        for (Map.Entry<String, List<Map>> entry : data.entrySet()){
            //sheet名 销售商名字
            String sheetName = entry.getKey();
            if ("".equals(sheetName)){
                continue;
            }
            //创建HSSFWorkbook
            List<Map> sellerList = entry.getValue();
            // TODO: 2018/11/8 导出
//            exportXSSFWorkbook(sheetName, title, sellerList, wb);
        }
        this.setResponseHeader(response, fileName);
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * 发送响应流方法
     * @param response
     * @param fileName
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), StandardCharsets.UTF_8);
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
