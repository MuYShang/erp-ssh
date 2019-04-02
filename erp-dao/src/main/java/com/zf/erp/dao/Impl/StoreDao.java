package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IStoreDao;
import com.zf.erp.domain.Store;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 商品仓库持久层实现类
 */
public class StoreDao extends BaseDao<Store> implements IStoreDao {

    /**
     * 构建查询条件
     * @param param
     * @return
     */
    public DetachedCriteria deta(Store store1, Store store2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Store.class);
        if(store1!=null){
            if(null != store1.getName() && store1.getName().trim().length()>0){
                dc.add(Restrictions.like("name", store1.getName(), MatchMode.ANYWHERE));
            }
            //根据员工编号查询
            if(null != store1.getEmpuuid()){
                dc.add(Restrictions.eq("empuuid", store1.getEmpuuid()));
            }

        }
        return dc;
    }


}
