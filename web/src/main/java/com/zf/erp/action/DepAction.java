package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zf.erp.Biz.IDepBiz;
import com.zf.erp.domain.Dep;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepAction extends BaseAction<Dep> {

    private IDepBiz depBiz;

    @Resource(name = "DepBiz")
    public void setDepBiz(IDepBiz depBiz) {
        this.depBiz = depBiz;
        super.setiBaseBiz(depBiz);
    }

    private Dep dep = new Dep();



    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        dep = depBiz.get(this.dep.getUuid());
        super.setT(dep);
        super.get();

    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(dep.getUuid());
        super.del();
    }


    @Override
    public Dep getModel() {
        super.setT(dep);
        return dep;
    }
}
