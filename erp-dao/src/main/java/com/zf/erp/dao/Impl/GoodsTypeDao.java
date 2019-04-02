package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IGoodsTypeDao;
import com.zf.erp.domain.Goodstype;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 商品类型持久层实现类
 */
public class GoodsTypeDao extends BaseDao<Goodstype> implements IGoodsTypeDao{

    public DetachedCriteria deta(Goodstype goodstype1, Goodstype goodstype2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Goodstype.class);
        if(goodstype1!=null){
            if(null != goodstype1.getName() && goodstype1.getName().trim().length()>0){
                dc.add(Restrictions.like("name", goodstype1.getName(), MatchMode.ANYWHERE));
            }

        }
        return dc;
    }
}
