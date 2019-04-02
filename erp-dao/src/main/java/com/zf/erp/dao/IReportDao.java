package com.zf.erp.dao;

import java.util.Date;
import java.util.List;

/**
 * 统计报表持久层接口
 */
public interface IReportDao {

    List ordersReport(Date starttime, Date endtime);

    List getSumMoney(int month);

}
