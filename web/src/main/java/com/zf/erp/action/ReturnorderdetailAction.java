package com.zf.erp.action;

import com.zf.erp.Biz.IReturnorderdetailBiz;
import com.zf.erp.domain.Emp;
import com.zf.erp.domain.Returnorderdetail;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 退货订单明细web层
 */
public class ReturnorderdetailAction extends BaseAction<Returnorderdetail> {

    private IReturnorderdetailBiz returnorderdetailBiz;


    @Resource(name = "ReturnorderdetailBiz")
    public void setReturnorderdetailBiz(IReturnorderdetailBiz returnorderdetailBiz) {
        this.returnorderdetailBiz = returnorderdetailBiz;
        super.setiBaseBiz(returnorderdetailBiz);
    }

    private Returnorderdetail returnorderdetail = new Returnorderdetail();


    //退货订单审核
    public void quitOutStore(){
        Integer storeuuid = returnorderdetail.getStoreuuid();
        returnorderdetail = returnorderdetailBiz.get(returnorderdetail.getUuid());
        returnorderdetail.setStoreuuid(storeuuid);
        System.out.println(returnorderdetail);
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        try {
            returnorderdetail.setEnder(login.getUuid());
            returnorderdetailBiz.quitOutStore(returnorderdetail,login.getUuid());
            ajaxReturn(true,"出库成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"出库失败");
        }
    }

    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        returnorderdetail = returnorderdetailBiz.get(this.returnorderdetail.getUuid());
        super.setT(returnorderdetail);
        super.get();

    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(returnorderdetail.getUuid());
        super.del();
    }


    @Override
    public Returnorderdetail getModel() {
        super.setT(returnorderdetail);
        return returnorderdetail;
    }
}
