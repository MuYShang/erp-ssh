package com.zf.erp.Biz;

import java.util.Date;
import java.util.List;

/**
 * 统计报表业务层接口
 */
public interface IReportBiz {

    List ordersReport(Date starttime, Date endtime);

    List getSumMoney(int month);

}
