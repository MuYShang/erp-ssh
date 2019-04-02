package com.zf.erp.action;

import com.zf.erp.Biz.IStoreBiz;
import com.zf.erp.domain.Emp;
import com.zf.erp.domain.Store;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 商品仓库w
 */
public class StoreAction  extends BaseAction<Store> {

    private IStoreBiz storeBiz;

    @Resource(name = "StoreBiz")
    public void setStoreBiz(IStoreBiz storeBiz) {
        this.storeBiz = storeBiz;
        super.setiBaseBiz(storeBiz);
    }

    private Store store = new Store();



    /**
     * 获取某个仓库信息
     */
    public void get() throws Exception {

        store = storeBiz.get(this.store.getUuid());
        super.setT(store);
        super.get();

    }


    public void getAddList(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        store.setEmpuuid(login.getUuid());

        List<Store> list = storeBiz.getList(store,null,null,0,0);

        write(list);
    }

    /**
     * 删除仓库
     */
    public void del(){
        super.setUuid(store.getUuid());
        super.del();
    }


    @Override
    public Store getModel() {
        super.setT(store);
        return store;
    }
}
