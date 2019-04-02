package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.ISupplierBiz;
import com.zf.erp.dao.ISupplierDao;
import com.zf.erp.domain.Supplier;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

    private ISupplierDao iSupplierDao;

    public void setiSupplierDao(ISupplierDao iSupplierDao) {
        this.iSupplierDao = iSupplierDao;
        super.setiBaseDao(iSupplierDao);
    }

    //实现Excel导出功能
    @Override
    public void export(OutputStream os, Supplier supplier1) {
        List<Supplier> list = iSupplierDao.getList(supplier1, null, null, 0, 0);
        //获取工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        //获取工作表
        HSSFSheet sheet = wb.createSheet();
        //写入表头
        String[] titleArr = {"名称","联系地址","联系人","联系电话"," 邮件地址"};
        //定义列宽
        int[] cellWidth = {5000,8000,5000,8000,10000};
        int Rowindex = 0;
        HSSFRow row = sheet.createRow(Rowindex);

        for (int i = 0;i<titleArr.length;i++){
            //写入数据
            row.createCell(i).setCellValue(titleArr[i]);
            //设置列宽
            sheet.setColumnWidth(i,cellWidth[i]);
        }

        //写入读取的数据
        for (Supplier supplier : list) {
            Rowindex += 1;
            row = sheet.createRow(Rowindex);
            row.createCell(0).setCellValue(supplier.getName());
            row.createCell(1).setCellValue(supplier.getAddress());
            row.createCell(2).setCellValue(supplier.getContact());
            row.createCell(3).setCellValue(supplier.getTele());
            row.createCell(4).setCellValue(supplier.getEmail());
        }

        try {
            wb.write(os);
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

    /**
     * 供应商/客户导入功能
     * @param is
     */
    @Override
    public void doImport(InputStream is) throws Exception {
        //创建工作簿对象
        HSSFWorkbook wb = null;
        try {
            //将输入流导入工作簿对象
            wb = new HSSFWorkbook(is);
            //获取工作表
            HSSFSheet sheet = wb.getSheetAt(0);
            String type = "";
            if("供应商".equals(sheet.getSheetName())){
                type = "1";
            }else if("客户".equals(sheet.getSheetName())){
                type = "2";
            }else{
                throw new Exception("文件格式错误");
            }
            //获取导入文件中最大行
            int lastRowNum = sheet.getLastRowNum();
            Supplier supplier = null;
            for (int i = 1;i<=lastRowNum;i++){
                supplier = new Supplier();
                //获取导入文件中姓名
                String name = sheet.getRow(i).getCell(0).getStringCellValue();
                //查找是否有同名
                supplier.setName(name);
                List<Supplier> list = iSupplierDao.getList(null, supplier, null, 0, 0);
                if(list.size() > 0){
                    supplier = list.get(0);
                }
                supplier.setAddress(sheet.getRow(i).getCell(1).getStringCellValue());//地址
                supplier.setContact(sheet.getRow(i).getCell(2).getStringCellValue());//联系人
                supplier.setTele(sheet.getRow(i).getCell(3).getStringCellValue());//电话
                supplier.setEmail(sheet.getRow(i).getCell(4).getStringCellValue());//邮箱

                System.out.println(supplier);
                if(list.size() == 0){
                    supplier.setType(type);//类型
                    iSupplierDao.add(supplier);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
