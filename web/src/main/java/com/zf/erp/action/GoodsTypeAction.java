package com.zf.erp.action;

import com.zf.erp.Biz.IGoodsTypeBiz;
import com.zf.erp.domain.Goodstype;

import javax.annotation.Resource;

public class GoodsTypeAction extends BaseAction<Goodstype> {

    private IGoodsTypeBiz igoodsTypeBiz;

    @Resource(name = "GoodsTypeBiz")
    public void setGoodsBiz(IGoodsTypeBiz igoodsTypeBiz) {
        this.igoodsTypeBiz = igoodsTypeBiz;
        super.setiBaseBiz(igoodsTypeBiz);
    }

    private Goodstype goodstype = new Goodstype();


    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        goodstype = igoodsTypeBiz.get(goodstype.getUuid());

        super.setT(goodstype);
        super.get();
    }


    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(goodstype.getUuid());
        super.del();
    }


    @Override
    public Goodstype getModel() {
        super.setT(goodstype);
        return goodstype;
    }
}
