package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IReturnorderdetailDao;
import com.zf.erp.domain.Returnorderdetail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 退货订单明细持久层实现类
 */
public class ReturnorderdetailDao extends BaseDao<Returnorderdetail> implements IReturnorderdetailDao {

    /**
     * 构建查询条件
     * @param param
     * @return
     */
    public DetachedCriteria deta(Returnorderdetail returnorderdetail1, Returnorderdetail returnorderdetail2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Returnorderdetail.class);
        if(returnorderdetail1!=null){
            if(null != returnorderdetail1.getGoodsname() && returnorderdetail1.getGoodsname().trim().length()>0){
                dc.add(Restrictions.like("goodsname", returnorderdetail1.getGoodsname(), MatchMode.ANYWHERE));
            }
            if(null != returnorderdetail1.getState() && returnorderdetail1.getState().trim().length()>0){
                dc.add(Restrictions.eq("state", returnorderdetail1.getState()));
            }
            //根据订单查询
            if(null != returnorderdetail1.getReturnorders() && null != returnorderdetail1.getReturnorders().getUuid()){
                dc.add(Restrictions.eq("returnorders", returnorderdetail1.getReturnorders()));
            }

        }
        return dc;
    }

}
