package com.xdd.springrvmp.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author AixLeft
 * Date 2021/1/22
 */
public class ImportExcel {

    /**
     * Excel导入数据
     * @param is
     * @return
     * @throws IOException
     */
    public static List<List<String>> readExcel(InputStream is) throws IOException {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** 得到第一个sheet */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        int totalRows = sheet.getPhysicalNumberOfRows();

        /** 得到Excel的列数 */
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 循环Excel的行 从第三行开始*/
        for (int r = 2; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if(row == null){
                continue;
            }

            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                     /*HSSFDataFormatter hSSFDataFormatter = new HSSFDataFormatter();
                     cellValue= hSSFDataFormatter.formatCellValue(cell);*/

                    // 以下是判断数据的类型
                    CellType type = cell.getCellTypeEnum();

                    switch (type) {
                        case NUMERIC: // 数字
                            cellValue = cell.getNumericCellValue() + "";
                            break;
                        case STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;
                        case BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        case FORMULA: // 公式
                            try {
                                cellValue = cell.getStringCellValue();
                            } catch (IllegalStateException e) {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                       /* cellValue = cell.getCellFormula() + "";
                        break;*/
                        case BLANK: // 空值
                            cellValue = "";
                            break;
                        case _NONE: // 故障
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }

}
