package com.zf.erp.Biz;

import com.zf.erp.domain.Orders;

import java.io.OutputStream;
import java.util.List;

/**
 * 订单业务层接口
 */
public interface IOrdersBiz extends IBaseBiz<Orders> {

    //审核订单
    void doCheck(Orders orders);

    //确认订单
    void doStart(Orders orders);

    //导出Excel功能
    void export(OutputStream os, Integer uuid);
}
