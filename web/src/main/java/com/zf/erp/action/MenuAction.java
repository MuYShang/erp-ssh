package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.zf.erp.Biz.IMenuBiz;
import com.zf.erp.domain.Menu;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MenuAction extends BaseAction<Menu>{

    private IMenuBiz imenuBiz;

    @Resource(name = "MenuBiz")
    public void setMenuBiz(IMenuBiz imenuBiz) {
        this.imenuBiz = imenuBiz;
        super.setiBaseBiz(imenuBiz);
    }

    private Menu menu = new Menu();


    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {
        System.out.println(menu.getMenuid());

        menu = imenuBiz.get(menu.getMenuid());

        super.setT(menu);

        super.get();

    }

    public void getMenuTrees(){

        menu = imenuBiz.get("0");
        menu.getMenus().remove(0);
        String jsonString = JSON.toJSONString(menu);

        //设置编码格式
        System.out.println(jsonString);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");

        //获取response并将数据写入
        try {
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(menu.getMenuid());
        super.del();
    }



    public void write(Object list){

        SimplePropertyPreFilter simplePropertyPreFilter =
                new SimplePropertyPreFilter(Menu.class, "menuid", "menuname","pid","icon","url");
        //将列表数据封装成JSON对象
        String jsonString = JSON.toJSONString(list,simplePropertyPreFilter,SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(jsonString);
        //设置编码格式
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");

        //获取response并将数据写入
        try {
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Menu getModel() {
        super.setT(menu);
        return menu;
    }
}
