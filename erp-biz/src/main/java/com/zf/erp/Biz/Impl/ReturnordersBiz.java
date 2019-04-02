package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IReturnordersBiz;
import com.zf.erp.dao.*;
import com.zf.erp.domain.*;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退货订单业务层实现类
 */
public class ReturnordersBiz extends BaseBiz<Returnorders> implements IReturnordersBiz {

    private IReturnordersDao iReturnordersDao;

    @Setter
    private IEmpDao iEmpDao;

    @Setter
    private ISupplierDao iSupplierDao;


    public void setiReturnordersDao(IReturnordersDao iReturnordersDao) {
        this.iReturnordersDao = iReturnordersDao;
        super.setiBaseDao(iReturnordersDao);
    }

    //退货订单登记
    @Override
    public void quitCreate(Returnorders returnorders) {
        //在此处完成录入日期，总金额，订单状态的登记

        //录入日期
        returnorders.setCreatetime(new Date());

        List<Returnorderdetail> list = returnorders.getReturnorderdetails();
        double totalmoney = 0;
        //读取列表中的金额累加总金额
        for (Returnorderdetail returnorderdetail : list) {

            totalmoney += returnorderdetail.getMoney();

            //设置退货订单明细中的状态
            returnorderdetail.setState(Returnorderdetail.STATE_NOT_OUT);

            returnorderdetail.setReturnorders(returnorders);
        }

        //当前状态
        returnorders.setState(Returnorders.STATE_CREATE);

        returnorders.setTotalmoney(totalmoney);

        iReturnordersDao.add(returnorders);
    }

    //退货订单审核
    @Override
    public void quitCheck(Returnorders returnorders) {
        //在此完成对退货订单的审核时间，订单状态登记
        returnorders.setChecktime(new Date());
        returnorders.setState(Returnorders.STATE_CHECK);
    }


    @Override
    public List<Returnorders> getList(Returnorders t1, Returnorders t2, Object param, int firstResult, int maxResults) {
        List<Returnorders> returnordersList = super.getList(t1, t2, param, firstResult, maxResults);

        //缓冲区
        Map<Integer,String> empNameMap = new HashMap<>();
        Map<Integer,String> supplierNameMap = new HashMap<>();

        //遍历列表，并为其中的属性赋值
        for (Returnorders returnorders : returnordersList) {

            //为对应名称属性赋值
            returnorders.setCheckerName(getName(returnorders.getChecker(),empNameMap,iEmpDao));
            returnorders.setCreaterName(getName(returnorders.getCreater(),empNameMap,iEmpDao));
            returnorders.setEnderName(getName(returnorders.getEnder(),empNameMap,iEmpDao));

            returnorders.setSupplierName(getName(returnorders.getSupplieruuid(),supplierNameMap,iSupplierDao));
        }


        return returnordersList;
    }


}
