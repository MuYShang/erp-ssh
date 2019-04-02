package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IReportBiz;
import com.zf.erp.dao.IReportDao;

import java.util.*;

public class ReportBiz implements IReportBiz {
    private IReportDao iReportDao;

    public void setiReportDao(IReportDao iReportDao) {
        this.iReportDao = iReportDao;
    }

    @Override
    public List ordersReport(Date starttime, Date endtime) {
        return iReportDao.ordersReport(starttime, endtime);
    }

    @Override
    public List getSumMoney(int month) {
        List<Map<String,Object>> sumMoney = iReportDao.getSumMoney(month);
        Map<Integer,Map<String,Object>> monthMap = new HashMap<>();
        for (Map<String, Object> stringObjectMap : sumMoney) {
            monthMap.put((Integer) stringObjectMap.get("name"),stringObjectMap);
        }
        //在此处完成对月份数据的查漏补缺
        List<Map<String,Object>> tempList = new ArrayList<>();
        for (int i = 1;i<=12;i++){
            Map<String,Object> map = new HashMap<>();
            if(null == monthMap.get(i)){
                map.put("name",i + "月");
                map.put("y",0);
            }else{
                map.put("name",monthMap.get(i).get("name") + "月");
                map.put("y",monthMap.get(i).get("y"));
            }
            tempList.add(map);
        }

        return tempList;
    }


}
