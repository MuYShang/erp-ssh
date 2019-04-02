package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IReturnorderdetailBiz;
import com.zf.erp.dao.IReturnorderdetailDao;
import com.zf.erp.dao.IStoredetailDao;
import com.zf.erp.dao.IStoreoperDao;
import com.zf.erp.domain.*;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 退货订单明细业务层实现类
 */
public class ReturnorderdetailBiz extends BaseBiz<Returnorderdetail> implements IReturnorderdetailBiz {

    private IReturnorderdetailDao iReturnordertailDao;


    public void setiReturnordertailDao(IReturnorderdetailDao iReturnordertailDao) {
        this.iReturnordertailDao = iReturnordertailDao;
        super.setiBaseDao(iReturnordertailDao);
    }


    @Setter
    private IStoredetailDao iStoredetailDao;
    @Setter
    private IStoreoperDao iStoreoperDao;

    //退货订单登记
    @Override
    public void quitOutStore(Returnorderdetail returnorderdetail, Integer uuid) throws Exception {
        //第一步：判断该次退货是否满足条件
        Storedetail storedetail = new Storedetail();
        storedetail.setStoreuuid(returnorderdetail.getStoreuuid());
        storedetail.setGoodsuuid(returnorderdetail.getGoodsuuid());
        List<Storedetail> storedetailDaoList = iStoredetailDao.getList(storedetail, null, null, 0, 0);
        //如果商品仓库中没有该项数据
        if(storedetailDaoList.size() <= 0){
            throw new Exception("商品仓库中没有该项数据");
        }
        Storedetail num_store = storedetailDaoList.get(0);
        //如果订单中该商品退货数量大于仓库数量
        if(returnorderdetail.getNum() > num_store.getNum()){
            throw new Exception("该商品退货数量大于仓库数量");
        }
        //修改商品仓库数据
        int num = num_store.getNum() - returnorderdetail.getNum();
        num_store.setNum(num);

        //第二步：
        if(Returnorderdetail.STATE_OUT.equals(returnorderdetail.getState())){
            throw new Exception("不能重复入库");
        }
        //设置属性
        returnorderdetail.setEndtime(new Date());
        returnorderdetail.setState(Returnorderdetail.STATE_OUT);
        iReturnordertailDao.update(returnorderdetail);

        //第三步
        //插入库存变更记录
        //配置信息
        Storeoper storeoper = new Storeoper();
        storeoper.setEmpuuid(uuid);
        storeoper.setGoodsuuid(returnorderdetail.getGoodsuuid());
        storeoper.setOpertime(new Date());
        storeoper.setNum(num);
        storeoper.setStoreuuid(returnorderdetail.getStoreuuid());
        storeoper.setType(Storeoper.TYPE_IN);
        iStoreoperDao.add(storeoper);

        /**
         * 第四步
         */
        //检查订单中所有商品是否完成出库
        Returnorderdetail count = new Returnorderdetail();
        count.setState(Returnorderdetail.STATE_NOT_OUT);
        Returnorders returnorders = returnorderdetail.getReturnorders();
        count.setReturnorders(returnorders);
        Long daoCount = iReturnordertailDao.getCount(count, null, null);
        if(daoCount == 0){
            returnorders.setEndtime(new Date());
            returnorders.setState(Returnorders.STATE_END);
            returnorders.setEnder(uuid);
        }

    }


}
