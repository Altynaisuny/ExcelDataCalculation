package com.example.excel.facade;

import com.example.excel.domain.enumeration.orderScreenEnum;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.*;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CommonMethods {
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            // 数字
            case Cell.CELL_TYPE_NUMERIC:
                short format = cell.getCellStyle().getDataFormat();
                // 判断当前的cell是否为Date
                if (format == 14 || format == 31 || format == 57 || format == 58) {
                    //excel中的时间格式
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(value);
                    cellValue = sdf.format(date);
                } else if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                    // 如果是Date类型则，取得该Cell的Date值
                    // 对2014-02-02格式识别不出是日期格式
                    Date date = cell.getDateCellValue();
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = formater.format(date);
                } else { // 如果是纯数字
                    // 取得当前Cell的数值
                    cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            // 字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            // Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            // 公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 判断文件格式
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream in, String fileName) throws Exception {
        Workbook workbook;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            //excel 2003
            workbook = new HSSFWorkbook(in);
        } else if (".xlsx".equals(fileType)) {
            //excel 2007
            workbook = new XSSFWorkbook(in);
        } else {
            workbook = null;
        }
        return workbook;
    }

    public static XSSFCellStyle sheetStyle(XSSFWorkbook wb, String styleStr){
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();

        style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框

        style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        XSSFFont font = (XSSFFont) wb.createFont();//创建字体对象
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)11);
        style.setFont(font);

        if ("header".equals(styleStr)){
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        }
        return style;
    }
}
