package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IMenuBiz;
import com.zf.erp.dao.IMenuDao;
import com.zf.erp.domain.Menu;

public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

    private IMenuDao iMenuDao;

    public void setiMenuDao(IMenuDao iMenuDao) {
        this.iMenuDao = iMenuDao;
        super.setiBaseDao(iMenuDao);
    }
}
