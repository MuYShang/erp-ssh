package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.zf.erp.Biz.IInventoryBiz;
import com.zf.erp.domain.*;
import com.zf.erp.domain.Inventory;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

public class InventoryAction extends BaseAction<Inventory> {

    private IInventoryBiz inventoryBiz;

    @Resource(name = "InventoryBiz")
    public void setInventoryBiz(IInventoryBiz inventoryBiz) {
        this.inventoryBiz = inventoryBiz;
        super.setiBaseBiz(inventoryBiz);
    }

    private Inventory inventory = new Inventory();


    public void myListByPage(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录.");
            return;
        }

        //设置下单用户

        inventory.setCreater(login.getUuid());
        super.setT(inventory);
        super.getList();
    }

    @Override
    public void add() {

        try {
            Inventory inventory = getT();

            HttpSession session = ServletActionContext.getRequest().getSession();
            //判断当前是否已经登录
            Emp login = (Emp) session.getAttribute("isLogin");
            if (null == login) {
                ajaxReturn(false, "当前未登录！");
                return;
            }
            //设置下单用户
            inventory.setCreater(login.getUuid());

            inventoryBiz.add(inventory);
            ajaxReturn(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"添加失败");
        }


    }

    //审核
    public void doCheck(){
        //根据UUID获取对象
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        inventory = inventoryBiz.get(inventory.getUuid());

        try {
            //如果当前订单已经审核
            if(Inventory.STATE_END.equals(inventory.getState())){
                //抛出异常
                throw new Exception("当前订单已经审核");
            }
            //设置审核用户
            inventory.setChecker(login.getUuid());

            //开始审核
            inventoryBiz.doCheck(inventory);

            ajaxReturn(true,"审核成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"审核失败");
        }
    }


    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        inventory = inventoryBiz.get(this.inventory.getUuid());
        super.setT(inventory);
        super.get();

    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(inventory.getUuid());
        super.del();
    }


    @Override
    public Inventory getModel() {
        super.setT(inventory);
        return inventory;
    }
}
