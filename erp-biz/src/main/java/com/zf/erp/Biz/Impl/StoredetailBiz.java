package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IStoredetailBiz;
import com.zf.erp.dao.IGoodsDao;
import com.zf.erp.dao.IStoreDao;
import com.zf.erp.dao.IStoredetailDao;
import com.zf.erp.domain.Storedetail;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

    private IStoredetailDao iStoredetailDao;
    @Getter
    @Setter
    private IGoodsDao iGoodsDao;
    @Getter
    @Setter
    private IStoreDao iStoreDao;

    public void setiStoredetailDao(IStoredetailDao iStoredetailDao) {
        this.iStoredetailDao = iStoredetailDao;
        super.setiBaseDao(iStoredetailDao);
    }

    @Override
    public List<Storedetail> getList(Storedetail t1, Storedetail t2, Object param, int firstResult, int maxResults) {
        List<Storedetail> storedetailList = super.getList(t1, t2, param, firstResult, maxResults);

        //缓冲区
        Map<Integer,String> goodsNameMap = new HashMap<>();
        Map<Integer,String> storeNameMap = new HashMap<>();

        //遍历列表，并为其中的属性赋值
        for (Storedetail storedetail : storedetailList) {

            //为对应名称属性赋值
            storedetail.setGoodsName(getName(storedetail.getGoodsuuid(),goodsNameMap,iGoodsDao));
            storedetail.setStoreName(getName(storedetail.getStoreuuid(),storeNameMap,iStoreDao));

        }

        return storedetailList;
    }

}
