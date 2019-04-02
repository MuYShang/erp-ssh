package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IReturnordersDao;
import com.zf.erp.domain.Returnorders;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 退货订单实现类
 */
public class ReturnordersDao extends BaseDao<Returnorders> implements IReturnordersDao {

    /**
     * 构建查询条件
     * @param param
     * @return
     */
    public DetachedCriteria deta(Returnorders returnorders1, Returnorders returnorders2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Returnorders.class);
        if(returnorders1!=null){
            if(null != returnorders1.getType() && returnorders1.getType().trim().length()>0){
                dc.add(Restrictions.like("type", returnorders1.getType(), MatchMode.ANYWHERE));
            }
            if(null != returnorders1.getState() && returnorders1.getState().trim().length()>0){
                dc.add(Restrictions.like("state", returnorders1.getState(), MatchMode.ANYWHERE));
            }
            if(null != returnorders1.getCreater()){
                dc.add(Restrictions.eq("creater", returnorders1.getCreater()));
            }

        }
        return dc;
    }


}
