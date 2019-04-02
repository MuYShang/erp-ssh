package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IStoreoperBiz;
import com.zf.erp.dao.IEmpDao;
import com.zf.erp.dao.IGoodsDao;
import com.zf.erp.dao.IStoreDao;
import com.zf.erp.dao.IStoreoperDao;
import com.zf.erp.domain.Storeoper;
import com.zf.erp.domain.Storeoper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreoperBiz extends BaseBiz<Storeoper> implements IStoreoperBiz {

    private IStoreoperDao iStoreoperDao;
    @Getter
    @Setter
    private IStoreDao iStoreDao;
    @Getter
    @Setter
    private IGoodsDao iGoodsDao;
    @Getter
    @Setter
    private IEmpDao iEmpDao;

    public void setiStoreoperDao(IStoreoperDao iStoreoperDao) {
        this.iStoreoperDao = iStoreoperDao;
        super.setiBaseDao(iStoreoperDao);
    }


    @Override
    public List<Storeoper> getList(Storeoper t1, Storeoper t2, Object param, int firstResult, int maxResults) {
        List<Storeoper> storeopers = super.getList(t1, t2, param, firstResult, maxResults);

        //缓冲区
        Map<Integer,String> empNameMap = new HashMap<>();
        Map<Integer,String> storeNameMap = new HashMap<>();
        Map<Integer,String> goodsNameMap = new HashMap<>();

        //遍历列表，并为其中的属性赋值
        for (Storeoper storeoper : storeopers) {

            storeoper.setGoodsName(getName(storeoper.getGoodsuuid(),goodsNameMap,iGoodsDao));
            storeoper.setEmpName(getName(storeoper.getEmpuuid(),empNameMap,iEmpDao));
            storeoper.setStoreName(getName(storeoper.getStoreuuid(),storeNameMap,iStoreDao));

        }


        return storeopers;
    }


}
