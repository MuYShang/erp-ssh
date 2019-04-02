package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IMenuDao;
import com.zf.erp.domain.Menu;
import org.hibernate.criterion.DetachedCriteria;

public class MenuDao extends BaseDao<Menu> implements IMenuDao {

    /**
     * 构建查询条件
     */
    @Override
    public DetachedCriteria deta(Menu goods1, Menu goods2, Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Menu.class);
        return dc;
    }
}
