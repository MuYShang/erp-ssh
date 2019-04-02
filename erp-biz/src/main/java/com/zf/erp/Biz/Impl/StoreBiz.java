package com.zf.erp.Biz.Impl;


import com.zf.erp.Biz.IStoreBiz;
import com.zf.erp.dao.IStoreDao;
import com.zf.erp.domain.Store;

/**
 * 商品仓库业务层实现类
 */
public class StoreBiz extends BaseBiz<Store> implements IStoreBiz {

    private IStoreDao iStoreDao;

    public void setiStoreDao(IStoreDao iStoreDao) {
        this.iStoreDao = iStoreDao;
        super.setiBaseDao(iStoreDao);
    }


}
