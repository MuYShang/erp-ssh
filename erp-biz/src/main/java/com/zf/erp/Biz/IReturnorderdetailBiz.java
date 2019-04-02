package com.zf.erp.Biz;


import com.zf.erp.domain.Returnorderdetail;

/**
 * 退货订单明细业务层接口
 */
public interface IReturnorderdetailBiz extends IBaseBiz<Returnorderdetail> {

    void quitOutStore(Returnorderdetail returnorderdetail, Integer uuid) throws Exception;
}
