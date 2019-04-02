package com.zf.erp.action;

import com.zf.erp.Biz.IStoredetailBiz;
import com.zf.erp.domain.Storedetail;

import javax.annotation.Resource;

/**
 * 仓库明细web层
 */
public class StoredetailAction extends BaseAction<Storedetail> {

    private IStoredetailBiz storedetailBiz;

    @Resource(name = "StoredetailBiz")
    public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
        this.storedetailBiz = storedetailBiz;
        super.setiBaseBiz(storedetailBiz);
    }

    private Storedetail storedetail = new Storedetail();



    /**
     * 获取某个仓库明细信息
     */
    public void get() throws Exception {

        storedetail = storedetailBiz.get(this.storedetail.getUuid());
        super.setT(storedetail);
        super.get();

    }

    /**
     * 删除仓库明细
     */
    public void del(){
        super.setUuid(storedetail.getUuid());
        super.del();
    }


    @Override
    public Storedetail getModel() {
        super.setT(storedetail);
        return storedetail;
    }
}
