package com.zf.erp.action;

import com.zf.erp.Biz.IGoodsBiz;
import com.zf.erp.domain.Goods;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsAction extends BaseAction<Goods>{

    private IGoodsBiz goodsBiz;

    @Resource(name = "GoodsBiz")
    public void setGoodsBiz(IGoodsBiz goodsBiz) {
        this.goodsBiz = goodsBiz;
        super.setiBaseBiz(goodsBiz);
    }

    private Goods goods = new Goods();


    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        goods = goodsBiz.get(goods.getUuid());

        super.setT(goods);
        super.get();
    }


    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(goods.getUuid());
        super.del();
    }

    public void getAddList(){

        List<Goods> list = goodsBiz.getList(goods,null,null,0,0);

        write(list);
    }


    @Override
    public Goods getModel() {
        super.setT(goods);
        System.out.println(goods);
        return goods;
    }
}
