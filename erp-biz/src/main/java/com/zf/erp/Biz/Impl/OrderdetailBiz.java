package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IOrderdetailBiz;
import com.zf.erp.dao.IOrderdetailDao;
import com.zf.erp.dao.IStoreDao;
import com.zf.erp.dao.IStoredetailDao;
import com.zf.erp.dao.IStoreoperDao;
import com.zf.erp.domain.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

    private IOrderdetailDao iOrderdetailDao;
    @Setter
    private IStoredetailDao iStoredetailDao;
    @Setter
    private IStoreoperDao iStoreoperDao;

    public void setiOrderdetailDao(IOrderdetailDao iOrderdetailDao) {
        this.iOrderdetailDao = iOrderdetailDao;
        super.setiBaseDao(iOrderdetailDao);
    }


    public void doInStore(Integer uuid,Integer empuuid,Integer storeuuid) throws Exception {

        /**第一步
         */
        //修改明细的状态为入库
        Orderdetail orderdetail = iOrderdetailDao.get(uuid);
        if(Orderdetail.STATE_IN.equals(orderdetail.getState())){
            throw new Exception("不能重复入库");
        }
        orderdetail.setState(Orderdetail.STATE_IN);
        //具体入到哪个仓库
        orderdetail.setStoreuuid(storeuuid);
        //入库时间
        orderdetail.setEndtime(new Date());
        //库管员
        orderdetail.setEnder(empuuid);


        /**
         * 第二步
         */
        //构建查询条件
        Storedetail storedetail = new Storedetail();
        storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
        storedetail.setStoreuuid(orderdetail.getStoreuuid());
        //查询库存中是否存在该商品
        List<Storedetail> list = iStoredetailDao.getList(storedetail, null, null, 0, 0);
        //如果存在即累加
        int num = 0;
        if(list.size() > 0){
            num = list.get(0).getNum() + orderdetail.getNum();
            list.get(0).setNum(num);
        }else{
            //否则新增记录
            num = orderdetail.getNum();
            storedetail.setNum(orderdetail.getNum());
            iStoredetailDao.add(storedetail);
        }

        /**
         * 第三步
         */
        //插入库存变更记录
        //配置信息
        Storeoper storeoper = new Storeoper();
        storeoper.setEmpuuid(empuuid);
        storeoper.setGoodsuuid(orderdetail.getGoodsuuid());
        storeoper.setOpertime(new Date());
        storeoper.setNum(num);
        storeoper.setStoreuuid(storeuuid);
        storeoper.setType(Storeoper.TYPE_IN);
        iStoreoperDao.add(storeoper);

        /**
         * 第四步
         */
        //检查订单中所有商品是否进入库存
        Orderdetail count = new Orderdetail();
        count.setState(Orderdetail.STATE_NOT_IN);
        Orders orders = orderdetail.getOrders();
        count.setOrders(orders);
        Long daoCount = iOrderdetailDao.getCount(count, null, null);
        if(daoCount == 0){
            orders.setEnder(empuuid);
            orders.setEndtime(new Date());
            orders.setState(Orders.STATE_END);
        }

    }

    public void doOutStore(Integer uuid,Integer empuuid,Integer storeuuid) throws Exception {

        /**第一步
         */
        //修改明细的状态为入库
        Orderdetail orderdetail = iOrderdetailDao.get(uuid);
        if(Orderdetail.STATE_OUT.equals(orderdetail.getState())){
            throw new Exception("不能重复出库");
        }
        orderdetail.setState(Orderdetail.STATE_OUT);
        //具体出自哪个仓库
        orderdetail.setStoreuuid(storeuuid);
        //出库时间
        orderdetail.setEndtime(new Date());
        //库管员
        orderdetail.setEnder(empuuid);


        /**
         * 第二步
         */
        //构建查询条件
        Storedetail storedetail = new Storedetail();
        storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
        storedetail.setStoreuuid(orderdetail.getStoreuuid());
        //查询库存中是否存在该商品
        List<Storedetail> list = iStoredetailDao.getList(storedetail, null, null, 0, 0);
        //如果存在即累加
        int num = 0;
        if(list.size() > 0){
            num = list.get(0).getNum() - orderdetail.getNum();
            if(num < 0){
                throw new Exception("库存不足");
            }
            list.get(0).setNum(num);
        }else{
            //否则提示仓库无该商品
            throw new Exception("仓库无该商品");
        }

        /**
         * 第三步
         */
        //插入库存变更记录
        //配置信息
        Storeoper storeoper = new Storeoper();
        storeoper.setEmpuuid(empuuid);
        storeoper.setGoodsuuid(orderdetail.getGoodsuuid());
        storeoper.setOpertime(new Date());
        storeoper.setNum(num);
        storeoper.setStoreuuid(storeuuid);
        storeoper.setType(Storeoper.TYPE_OUT);
        iStoreoperDao.add(storeoper);

        /**
         * 第四步
         */
        //检查订单中所有商品是否出库
        Orderdetail count = new Orderdetail();
        count.setState(Orderdetail.STATE_NOT_OUT);
        Orders orders = orderdetail.getOrders();
        count.setOrders(orders);
        Long daoCount = iOrderdetailDao.getCount(count, null, null);
        if(daoCount == 0){
            orders.setEnder(empuuid);
            orders.setEndtime(new Date());
            orders.setState(Orders.STATE_OUT);
        }

    }
}
