package com.zf.erp.action;

import com.zf.erp.Biz.IOrderdetailBiz;
import com.zf.erp.domain.Emp;
import com.zf.erp.domain.Orderdetail;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

public class OrderdetailAction extends BaseAction<Orderdetail> {

    private IOrderdetailBiz iorderdetail;

    @Resource(name = "OrderdetailBiz")
    public void setOrderdetailBiz(IOrderdetailBiz iorderdetail) {
        this.iorderdetail = iorderdetail;
        super.setiBaseBiz(iorderdetail);
    }

    private Orderdetail orderdetail = new Orderdetail();



    /**
     * 获取某个订单明细信息
     */
    public void get() throws Exception {

        orderdetail = iorderdetail.get(this.orderdetail.getUuid());
        super.setT(orderdetail);
        super.get();

    }


    public void doInStore(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        try {
            iorderdetail.doInStore(orderdetail.getUuid(),login.getUuid(),orderdetail.getStoreuuid());
            ajaxReturn(true,"入库成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"入库失败");
        }
    }

    public void doOutStore(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        try {
            iorderdetail.doOutStore(orderdetail.getUuid(),login.getUuid(),orderdetail.getStoreuuid());
            ajaxReturn(true,"出库成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"出库失败");
        }
    }

    /**
     * 删除订单明细
     */
    public void del(){
        super.setUuid(orderdetail.getUuid());
        super.del();
    }


    @Override
    public Orderdetail getModel() {
        super.setT(orderdetail);
        return orderdetail;
    }
}
