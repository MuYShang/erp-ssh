package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IGoodsBiz;
import com.zf.erp.dao.IGoodsDao;
import com.zf.erp.domain.Goods;
import org.springframework.transaction.annotation.Transactional;

public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

    private IGoodsDao iGoodsDao;

    public void setiGoodsDao(IGoodsDao iGoodsDao) {
        this.iGoodsDao = iGoodsDao;
        super.setiBaseDao(iGoodsDao);
    }
}
