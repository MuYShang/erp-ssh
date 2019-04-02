package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.zf.erp.Biz.IOrdersBiz;
import com.zf.erp.domain.Emp;
import com.zf.erp.domain.Orderdetail;
import com.zf.erp.domain.Orders;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 订单表现层
 */
public class OrdersAction extends BaseAction<Orders> {

    private IOrdersBiz iOrdersBiz;

    @Getter
    @Setter
    private String json;

    @Resource(name = "OrdersBiz")
    public void setOrdersBiz(IOrdersBiz iOrdersBiz) {
        this.iOrdersBiz = iOrdersBiz;
        super.setiBaseBiz(iOrdersBiz);
    }

    private Orders orders = new Orders();


    public void export(){
        String filename = "Orders_" + this.orders.getUuid() + ".xls";
        //响应对象
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            //设置输出流,实现下载文件
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(filename.getBytes(),"ISO-8859-1"));

            iOrdersBiz.export(response.getOutputStream(), this.orders.getUuid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        orders = iOrdersBiz.get(this.orders.getUuid());
        super.setT(orders);
        super.get();

    }


    @Override
    public void add() {

        try {
            Orders orders = getT();

            HttpSession session = ServletActionContext.getRequest().getSession();
            //判断当前是否已经登录
            Emp login = (Emp) session.getAttribute("isLogin");
            if (null == login) {
                ajaxReturn(false, "当前未登录！");
                return;
            }
            //设置下单用户
            orders.setCreater(login.getUuid());

            //将客户端传入的商品数据转换为对象
            List<Orderdetail> list = JSON.parseArray(json, Orderdetail.class);
            orders.setOrderdetails(list);

            iOrdersBiz.add(orders);
//            iOrdersBiz.addOrders(orders);
            ajaxReturn(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"添加失败");
        }


    }

    public void myListByPage(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录");
            return;
        }

        //设置下单用户

        orders.setCreater(login.getUuid());
        super.setT(orders);
        super.getList();
    }



    /**
     * 审核订单
     */
    public void doCheck(){
        //根据UUID获取对象
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        orders = iOrdersBiz.get(orders.getUuid());

        try {
            //如果当前订单已经审核
            if(Orders.STATE_CHECK.equals(orders.getState())){
                //抛出异常
                throw new Exception("当前订单已经审核");
            }
            //设置审核用户
            orders.setChecker(login.getUuid());

            //开始审核
            iOrdersBiz.doCheck(orders);

            ajaxReturn(true,"审核成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"审核失败");
        }
    }

    /**
     * 确认订单
     */
    public void doStart(){
        //根据UUID获取对象
        HttpSession session = ServletActionContext.getRequest().getSession();
        //判断当前是否已经登录
        Emp login = (Emp) session.getAttribute("isLogin");
        if (null == login) {
            ajaxReturn(false, "当前未登录！");
            return;
        }
        orders = iOrdersBiz.get(orders.getUuid());

        try {
            //如果当前订单已经确认
            if(Orders.STATE_START.equals(orders.getState())){
                //抛出异常
                throw new Exception("当前订单已经确认");
            }
            //设置确认用户
            orders.setStarter(login.getUuid());

            //开始审核
            iOrdersBiz.doStart(orders);

            ajaxReturn(true,"确认成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"审核失败");
        }
    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(orders.getUuid());
        super.del();
    }


    @Override
    public Orders getModel() {
        super.setT(orders);
        return orders;
    }


}
