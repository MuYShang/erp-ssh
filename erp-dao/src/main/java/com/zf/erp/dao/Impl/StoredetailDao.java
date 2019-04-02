package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IStoredetailDao;
import com.zf.erp.domain.Storedetail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class StoredetailDao extends BaseDao<Storedetail> implements IStoredetailDao {

    /**
     * 构建查询条件
     * @param param
     * @return
     */
    public DetachedCriteria deta(Storedetail storedetail1, Storedetail storedetail2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Storedetail.class);
        if(storedetail1!=null){
            //根据商品编号查询
            if(null != storedetail1.getGoodsuuid()){
                dc.add(Restrictions.eq("goodsuuid", storedetail1.getGoodsuuid()));
            }
            //根据仓库编号查询
            if(null != storedetail1.getStoreuuid()){
                dc.add(Restrictions.eq("storeuuid", storedetail1.getStoreuuid()));
            }
        }
        return dc;
    }

}
