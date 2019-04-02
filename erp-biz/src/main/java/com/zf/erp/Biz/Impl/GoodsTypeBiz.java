package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IGoodsTypeBiz;
import com.zf.erp.dao.IGoodsTypeDao;
import com.zf.erp.domain.Goodstype;

/**
 * 商品列表业务层实现类
 */
public class GoodsTypeBiz extends BaseBiz<Goodstype> implements IGoodsTypeBiz {

    private IGoodsTypeDao iGoodsTypeDao;

    public void setiGoodsTypeDao(IGoodsTypeDao iGoodsTypeDao) {
        this.iGoodsTypeDao = iGoodsTypeDao;
        super.setiBaseDao(iGoodsTypeDao);
    }
}
