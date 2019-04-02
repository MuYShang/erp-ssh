package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.zf.erp.Biz.IReturnordersBiz;
import com.zf.erp.domain.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 退货订单web层
 */
public class ReturnordersAction extends BaseAction<Returnorders> {

    private IReturnordersBiz returnordersBiz;

    @Resource(name = "ReturnordersBiz")
    public void setReturnordersBiz(IReturnordersBiz returnordersBiz) {
        this.returnordersBiz = returnordersBiz;
        super.setiBaseBiz(returnordersBiz);
    }

    private Returnorders returnorders = new Returnorders();

    @Getter
    @Setter
    private String json;



    /**
     * 退货申请登记
     */
    public void quitCreate(){
        try {
            Returnorders returnorders = getT();

            HttpSession session = ServletActionContext.getRequest().getSession();
            //判断当前是否已经登录
            Emp login = (Emp) session.getAttribute("isLogin");
            if (null == login) {
                ajaxReturn(false, "当前未登录！");
                return;
            }
            //设置订单创建用户
            returnorders.setCreater(login.getUuid());

            //将客户端传入的商品数据转换为对象
            List<Returnorderdetail> list = JSON.parseArray(json, Returnorderdetail.class);
            returnorders.setSupplieruuid(returnorders.getSupplieruuid());
            returnorders.setReturnorderdetails(list);

            returnordersBiz.quitCreate(returnorders);

            ajaxReturn(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"添加失败");
        }
    }

    /**
     * 退货申请审核
     */
    public void quitCheck(){
        returnorders = returnordersBiz.get(returnorders.getUuid());
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        try {
            returnorders.setChecker(login.getUuid());
            returnordersBiz.quitCheck(returnorders);
            ajaxReturn(true,"审核成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"审核失败");
        }
    }


    public void myListByPage(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }

        //设置下单用户
        returnorders.setCreater(login.getUuid());
        super.setT(returnorders);
        super.getList();
    }


    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        returnorders = returnordersBiz.get(this.returnorders.getUuid());
        super.setT(returnorders);
        super.get();

    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(returnorders.getUuid());
        super.del();
    }


    @Override
    public Returnorders getModel() {
        super.setT(returnorders);
        return returnorders;
    }
}
