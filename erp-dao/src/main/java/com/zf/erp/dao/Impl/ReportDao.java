package com.zf.erp.dao.Impl;


import com.zf.erp.dao.IReportDao;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 统计报表持久层实现类
 */
public class ReportDao extends HibernateDaoSupport implements IReportDao {


    @Override
    public List ordersReport(Date starttime,Date endtime) {
        List<Date> list = new ArrayList<>();
        String hql = "SELECT new  Map(gt.name as name,SUM(ol.money) as y) " +
                "FROM Goodstype gt,Goods gs,Orderdetail ol,Orders o " +
                "WHERE gs.goodstype=gt AND " +
                "ol.goodsuuid=gs.uuid " +
                "AND ol.orders=o and " +
                "o.type = '2' ";
        if(null != starttime){
            hql += "and o.createtime>=? ";
            list.add(starttime);
        }
        if(null != endtime){
            hql += "and o.createtime<=? ";
            list.add(endtime);
        }
        hql += "GROUP BY gt.name";
        return this.getHibernateTemplate().find(hql,list.toArray(new Date[]{}));
    }

    @Override
    public List getSumMoney(int month) {
        String hql = "select new Map( month(o.createtime) as name,sum(ol.money) as y)" +
                "from Orderdetail ol,Orders o " +
                "where ol.orders=o " +
                "and o.type='2' and year(o.createtime)=? " +
                "group by month(o.createtime)";
        return this.getHibernateTemplate().find(hql,month);
    }


}
