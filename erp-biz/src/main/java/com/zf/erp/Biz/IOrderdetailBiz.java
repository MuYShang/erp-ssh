package com.zf.erp.Biz;


import com.zf.erp.domain.Orderdetail;

/**
 * 订单明细业务层接口
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{

    void doInStore(Integer uuid,Integer empuuid,Integer storeuuid) throws Exception;

    void doOutStore(Integer uuid,Integer empuuid,Integer storeuuid) throws Exception;
}
