package com.zf.erp.action;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zf.erp.Biz.IReportBiz;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ReportAction {

    private IReportBiz iReportBiz;

    @Setter
    private Date starttime;
    @Setter
    private Date endtime;
    @Setter
    private int year;

    @Resource(name = "ReportBiz")
    public void setiReportBiz(IReportBiz iReportBiz) {
        this.iReportBiz = iReportBiz;
    }

    //获取统计报表数据
    public void ordersReport(){
        List list = iReportBiz.ordersReport(starttime,endtime);
        write(list);
    }
    //获取月份销售额
    public void getSumMoney(){
        List sumMoney = iReportBiz.getSumMoney(year);
        write(sumMoney);
    }

    public void write(Object list){
        //将列表数据封装成JSON对象
        String jsonString = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);

        //设置编码格式
        System.out.println(jsonString);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");

        //获取response并将数据写入
        try {
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
