package com.zf.erp.dao.Impl;


import com.zf.erp.dao.IOrderdetailDao;
import com.zf.erp.domain.Orderdetail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class OrderdetailDao extends BaseDao<Orderdetail> implements IOrderdetailDao {

    /**
     * 构建查询条件
     * @param param
     * @return
     */
    public DetachedCriteria deta(Orderdetail orderdetail1, Orderdetail orderdetail2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Orderdetail.class);
        if(orderdetail1!=null){
            if(null != orderdetail1.getGoodsname() && orderdetail1.getGoodsname().trim().length()>0){
                dc.add(Restrictions.like("goodsname", orderdetail1.getGoodsname(), MatchMode.ANYWHERE));
            }
            //根据状态查询
            if(null != orderdetail1.getState() && orderdetail1.getState().trim().length()>0){
                dc.add(Restrictions.eq("state", orderdetail1.getState()));
            }
            //根据订单查询
            if(null != orderdetail1.getOrders() && null != orderdetail1.getOrders().getUuid()){
                dc.add(Restrictions.eq("orders", orderdetail1.getOrders()));
            }

        }
        return dc;
    }


}
