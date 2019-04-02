package com.zf.erp.Biz;

import com.zf.erp.domain.Orderdetail;
import com.zf.erp.domain.Returnorderdetail;
import com.zf.erp.domain.Returnorders;

import java.util.List;

/**
 * 退货订单业务层接口
 */
public interface IReturnordersBiz extends IBaseBiz<Returnorders>{

    //退货订单登记
    void quitCreate(Returnorders returnorders);

    void quitCheck(Returnorders returnorders);

}
