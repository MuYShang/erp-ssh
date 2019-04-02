package com.zf.erp.QuartzTask;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;

public class POIDemo {

    //创建Excel示例
    public static void main(String[] args) {
        //创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建一个表
        HSSFSheet sheet = wb.createSheet();
        sheet.setColumnWidth(0,5000);
        //创建一行
        HSSFRow row = sheet.createRow(0);
        //获取单元格
        HSSFCell cell = row.createCell(0);

        cell.setCellValue("test");

        sheet.createRow(1).createCell(0).setCellValue("1111");
        String filename = "D://testxls.xls";
        File file = new File(filename);
        try {
            wb.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
