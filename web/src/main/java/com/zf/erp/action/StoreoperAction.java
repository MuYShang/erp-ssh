package com.zf.erp.action;

import com.zf.erp.Biz.IStoreoperBiz;
import com.zf.erp.domain.Storeoper;

import javax.annotation.Resource;

/**
 * 仓库变更记录web层
 */
public class StoreoperAction extends BaseAction<Storeoper> {

    private IStoreoperBiz storeoperBiz;

    @Resource(name = "StoreoperBiz")
    public void setStoreoperBiz(IStoreoperBiz storeoperBiz) {
        this.storeoperBiz = storeoperBiz;
        super.setiBaseBiz(storeoperBiz);
    }

    private Storeoper storeoper = new Storeoper();



    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        storeoper = storeoperBiz.get(this.storeoper.getUuid());
        super.setT(storeoper);
        super.get();

    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(storeoper.getUuid());
        super.del();
    }


    @Override
    public Storeoper getModel() {
        super.setT(storeoper);
        return storeoper;
    }
}
