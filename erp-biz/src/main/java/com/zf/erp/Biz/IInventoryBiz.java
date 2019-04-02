package com.zf.erp.Biz;

import com.zf.erp.domain.Inventory;

/**
 * 盘盈盘亏业务层接口
 */
public interface IInventoryBiz extends IBaseBiz<Inventory>{


    void doCheck(Inventory inventory);
}
