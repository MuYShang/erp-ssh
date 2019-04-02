package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IOrdersBiz;
import com.zf.erp.dao.IBaseDao;
import com.zf.erp.dao.IEmpDao;
import com.zf.erp.dao.IOrdersDao;
import com.zf.erp.dao.ISupplierDao;
import com.zf.erp.domain.Orderdetail;
import com.zf.erp.domain.Orders;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.criterion.Order;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 订单业务层实现类
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {


    private IOrdersDao iOrdersDao;
    @Getter
    @Setter
    private IEmpDao iEmpDao;
    @Getter
    @Setter
    private ISupplierDao iSupplierDao;

    public void setiOrdersDao(IOrdersDao iOrdersDao) {
        this.iOrdersDao = iOrdersDao;
        super.setiBaseDao(iOrdersDao);
    }

    @Override
    public void add(Orders orders) {

        //设置订单状态
        orders.setState(Orders.STATE_CREATE);

        //设置订单类型
//        orders.setType(Orders.TYPE_IN);

        //设置下单时间
        orders.setCreatetime(new Date());

        double money = 0;

        for (Orderdetail orderdetail : orders.getOrderdetails()) {
            //计算总金额
            money += orderdetail.getMoney();

            //订单明细状态
            orderdetail.setState(Orderdetail.STATE_NOT_IN);

            //设置订单关系
            orderdetail.setOrders(orders);

        }

        orders.setTotalmoney(money);

        iOrdersDao.add(orders);


    }

    @Override
    public List<Orders> getList(Orders t1, Orders t2, Object param, int firstResult, int maxResults) {
        List<Orders> ordersList = super.getList(t1, t2, param, firstResult, maxResults);

        //缓冲区
        Map<Integer,String> empNameMap = new HashMap<>();
        Map<Integer,String> supplierNameMap = new HashMap<>();

        //遍历列表，并为其中的属性赋值
        for (Orders orders : ordersList) {

            //为对应名称属性赋值
            orders.setCheckerName(getName(orders.getChecker(),empNameMap,iEmpDao));
            orders.setCreaterName(getName(orders.getCreater(),empNameMap,iEmpDao));
            orders.setEnderName(getName(orders.getEnder(),empNameMap,iEmpDao));
            orders.setStarterName(getName(orders.getStarter(),empNameMap,iEmpDao));

            orders.setSupplierName(getName(orders.getSupplieruuid(),supplierNameMap,iSupplierDao));
        }


        return ordersList;
    }


    //审核订单
    public void doCheck(Orders orders){

        //将订单改为已经审核
        orders.setState(Orders.STATE_CHECK);

        //设置审核时间
        orders.setChecktime(new Date());

        iOrdersDao.update(orders);

    }

    //确认订单
    public void doStart(Orders orders){

        //将订单改为已经确认
        orders.setState(Orders.STATE_START);

        //设置审核时间
        orders.setStarttime(new Date());

        iOrdersDao.update(orders);

    }

    @Override
    public void export(OutputStream os, Integer uuid) {
        //创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        //获取订单
        Orders orders = iOrdersDao.get(uuid);
        List<Orderdetail> detailList = orders.getOrderdetails();
        String sheetName = "";
        if(Orders.TYPE_IN.equals(orders.getType())){
            sheetName = "采 购 单";
        }
        if(Orders.TYPE_OUT.equals(orders.getType())){
            sheetName = "销 售 单";
        }
        //创建一个工作表
        HSSFSheet sheet = wb.createSheet(sheetName);
        //创建一行,行的索引是从0开始
        HSSFRow row = sheet.createRow(0);
        //创建内容体的单元格的样式
        HSSFCellStyle style_content = wb.createCellStyle();
        style_content.setBorderBottom(BorderStyle.THIN);//下边框
        style_content.setBorderTop(BorderStyle.THIN);//上边框
        style_content.setBorderLeft(BorderStyle.THIN);//左边框
        style_content.setBorderRight(BorderStyle.THIN);//右边框
        //对齐方式：水平居中
        style_content.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        style_content.setVerticalAlignment(VerticalAlignment.CENTER);
        //创建内容样式的字体
        HSSFFont font_content = wb.createFont();
        //设置字体名称，相当选中哪种字符
        font_content.setFontName("宋体");
        //设置字体的大小
        font_content.setFontHeightInPoints((short)11);
        style_content.setFont(font_content);

        //设置日期格式
        HSSFCellStyle style_date = wb.createCellStyle();
        //把 style_content里样式复制到date_style
        style_date.cloneStyleFrom(style_content);
        DataFormat df = wb.createDataFormat();
        style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));

        //标题样式
        HSSFCellStyle style_title = wb.createCellStyle();
        style_title.setAlignment(HorizontalAlignment.CENTER);
        style_title.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont style_font = wb.createFont();
        style_font.setFontName("黑体");
        style_font.setFontHeightInPoints((short)18);
        //加粗
        style_font.setBold(true);
        style_title.setFont(style_font);



        //合并单元格
        //标题：采购单
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        //供应商
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
        //订单明细
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 3));

        //创建矩阵 11行，4列
        int rowCount = detailList.size() + 9;
        for(int i = 2; i <= rowCount; i++){
            row = sheet.createRow(i);
            for(int j = 0; j < 4; j++){
                //设置单元格的样式
                row.createCell(j).setCellStyle(style_content);
            }
            //row.setHeight((short)500);
        }
        //必须先有创建的行和单元格
        //创建标题单元格
        HSSFCell titleCell = sheet.createRow(0).createCell(0);
        //设置标题样式
        titleCell.setCellStyle(style_title);
        titleCell.setCellValue(sheetName);

        sheet.getRow(2).getCell(0).setCellValue("供应商");
        sheet.getRow(3).getCell(0).setCellValue("下单日期");
        sheet.getRow(4).getCell(0).setCellValue("审核日期");
        sheet.getRow(5).getCell(0).setCellValue("采购日期");
        sheet.getRow(6).getCell(0).setCellValue("入库日期");
        sheet.getRow(3).getCell(2).setCellValue("经办人");
        sheet.getRow(4).getCell(2).setCellValue("经办人");
        sheet.getRow(5).getCell(2).setCellValue("经办人");
        sheet.getRow(6).getCell(2).setCellValue("经办人");

        sheet.getRow(7).getCell(0).setCellValue("订单明细");

        sheet.getRow(8).getCell(0).setCellValue("商品名称");
        sheet.getRow(8).getCell(1).setCellValue("数量");
        sheet.getRow(8).getCell(2).setCellValue("价格");
        sheet.getRow(8).getCell(3).setCellValue("金额");

        //设置行高
        //标题的行高
        sheet.getRow(0).setHeight((short)1000);
        //内容体的行高
        for(int i = 2; i <= rowCount; i++){
            sheet.getRow(i).setHeight((short)500);
        }
        //设置列宽
        for(int i = 0; i < 4; i++){
            sheet.setColumnWidth(i, 6000);
        }

        //缓存供应商编号与员工的名称, key=供应商的编号，value=供应商的名称
        Map<Integer, String> supplierNameMap = new HashMap<>();
        //设置供应商
        sheet.getRow(2).getCell(1).setCellValue(getName(orders.getSupplieruuid(), supplierNameMap,iSupplierDao));

        //订单详情, 设置日期
        sheet.getRow(3).getCell(1).setCellStyle(style_date);
        sheet.getRow(4).getCell(1).setCellStyle(style_date);
        sheet.getRow(5).getCell(1).setCellStyle(style_date);
        sheet.getRow(6).getCell(1).setCellStyle(style_date);
        if(null != orders.getCreatetime()){
            sheet.getRow(3).getCell(1).setCellValue(orders.getCreatetime());
        }
        if(null != orders.getChecktime()){
            sheet.getRow(4).getCell(1).setCellValue(orders.getChecktime());
        }
        if(null != orders.getStarttime()){
            sheet.getRow(5).getCell(1).setCellValue(orders.getStarttime());
        }
        if(null != orders.getEndtime()){
            sheet.getRow(6).getCell(1).setCellValue(orders.getEndtime());
        }


        //缓存员工编号与员工的名称, key=员工的编号，value=员工的名称
        Map<Integer, String> empNameMap = new HashMap<>();
        //设置经办人
        sheet.getRow(3).getCell(3).setCellValue(getName(orders.getCreater(),empNameMap, iEmpDao));
        sheet.getRow(4).getCell(3).setCellValue(getName(orders.getChecker(),empNameMap, iEmpDao));
        sheet.getRow(5).getCell(3).setCellValue(getName(orders.getStarter(),empNameMap, iEmpDao));
        sheet.getRow(6).getCell(3).setCellValue(getName(orders.getEnder(),empNameMap, iEmpDao));

        //设置明细内容
        int index = 0;
        Orderdetail od = null;
        for(int i = 9; i < rowCount; i++){
            od = detailList.get(index);
            row = sheet.getRow(i);
            row.getCell(0).setCellValue(od.getGoodsname());
            row.getCell(1).setCellValue(od.getNum());
            row.getCell(2).setCellValue(od.getPrice());
            row.getCell(3).setCellValue(od.getMoney());
            index++;
        }
        //设置合计
        sheet.getRow(rowCount).getCell(0).setCellValue("合计");
        sheet.getRow(rowCount).getCell(3).setCellValue(orders.getTotalmoney());

        //写到输出流里去
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
