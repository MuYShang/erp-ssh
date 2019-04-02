package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IInventoryBiz;
import com.zf.erp.dao.IEmpDao;
import com.zf.erp.dao.IGoodsDao;
import com.zf.erp.dao.IInventoryDao;
import com.zf.erp.dao.IStoreDao;
import com.zf.erp.domain.Inventory;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘盈盘亏业务层实现类
 */
public class InventoryBiz extends BaseBiz<Inventory> implements IInventoryBiz {

    private IInventoryDao iInventoryDao;

    @Setter
    private IEmpDao iEmpDao;
    @Setter
    private IStoreDao iStoreDao;
    @Setter
    private IGoodsDao iGoodsDao;


    public void setiInventoryDao(IInventoryDao iInventoryDao) {
        this.iInventoryDao = iInventoryDao;
        super.setiBaseDao(iInventoryDao);
    }

    //重写新增方法，在此处完成对记录基本信息的赋值
    @Override
    public void add(Inventory inventory) {
        //订单创建时间
        inventory.setCreatetime(new Date());

        //设置订单状态为创建
        inventory.setState(Inventory.STATE_CREATE);

        super.add(inventory);
    }

    @Override
    public List<Inventory> getList(Inventory t1, Inventory t2, Object param, int firstResult, int maxResults) {
        List<Inventory> inventoryList = super.getList(t1, t2, param, firstResult, maxResults);

        //缓冲区
        Map<Integer,String> empNameMap = new HashMap<>();
        Map<Integer,String> goodsNameMap = new HashMap<>();
        Map<Integer,String> storeNameMap = new HashMap<>();

        for (Inventory inventory : inventoryList) {

            inventory.setCreaterName(getName(inventory.getCreater(),empNameMap,iEmpDao));
            inventory.setCheckerName(getName(inventory.getChecker(),empNameMap,iEmpDao));
            inventory.setGoodsName(getName(inventory.getGoodsuuid(),goodsNameMap,iGoodsDao));
            inventory.setStoreName(getName(inventory.getStoreuuid(),storeNameMap,iStoreDao));

        }


        return inventoryList;
    }

    @Override
    public void doCheck(Inventory inventory) {
        inventory.setChecktime(new Date());
        inventory.setState(Inventory.STATE_END);
    }
}
